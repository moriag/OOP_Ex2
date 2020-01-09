package gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import algorithms.Graph_Algo;
import algorithms.graph_algorithms;

import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;


public class Graph_GUI extends JFrame implements ActionListener, MouseListener, MouseMotionListener {
    LinkedList<Point3D> mPoints = new LinkedList<Point3D>();
    Point3D mPivot_point = null;
    boolean mDraw_pivot = false;
    boolean mMoving_point = false;
    private int kRADIUS = 5;
    private graph _graph = null;
    private graph _graph_org= null;
    private int mod_count;
    private graph_algorithms _graphAlgo = null; 
    private int offset=100;
    private int scale = 2;
    private JComboBox<String> combobox; 
    private ArrayList<node_data> _shortestPath = null;
    private ArrayList<Integer> _tspTargets = new ArrayList<Integer>();
    
    // For shortest path
    int spSrc=-1;
    int spDest = -1;
    
    // For TSP
    public Graph_GUI(graph g) {
    	_graphAlgo = new Graph_Algo();
    	_graph_org=g;
    	_graphAlgo.init(g);
    	_graph = _graphAlgo.copy();
    	_graphAlgo.init(_graph);
    	initGUI();
    }
    
    private void clearAlgo() {
    	spSrc = -1; spDest = -1;
    	_shortestPath = null;
    	System.out.println("Selected: " + combobox.getSelectedItem());
    	_tspTargets.clear();
    	repaint();
    }
    
    private void initGUI() {
        this.setSize(600, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Menu");
        menuBar.add(menu);
        this.setMenuBar(menuBar);

        MenuItem item0 = new MenuItem("open");
        item0.addActionListener(this);

        MenuItem item1 = new MenuItem("save as");
        item1.addActionListener(this);

        menu.add(item0);
        menu.add(item1);

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        
        // Add panel
        JPanel panel = new JPanel();

        add(panel);

        JLabel lbl = new JLabel("Select one of the possible choices and click RUN");
        lbl.setVisible(true);
        panel.add(lbl);

        String[] choices = { "Shortest Path","TSP"};

        combobox = new JComboBox<String>(choices);
        combobox.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		clearAlgo();
            }
        });
        combobox.setVisible(true);
        panel.add(combobox);
        JButton btn = new JButton("Run");
        btn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(_graphAlgo == null) {
        			return;
        		}
        		// Shortest path
        		if(combobox.getSelectedItem() == "Shortest Path") {
        			if(spSrc == -1 || spDest == -1) {
        				System.out.println("Error! please choose src (left click) and dest (right click)");
        			}
        			else {
        				double shortestPathDist = _graphAlgo.shortestPathDist(spSrc, spDest);
        				_shortestPath = (ArrayList<node_data>)_graphAlgo.shortestPath(spSrc, spDest);
        				System.out.println("Shortest path: " + shortestPathDist);
        			}
        		}
        		else {
        			System.out.println("Running TSP");        			
        			_shortestPath = (ArrayList<node_data>)_graphAlgo.TSP(_tspTargets);
        			if(_shortestPath != null) {
        				System.out.println("Shortest path size:" + _shortestPath.size());
        			}
        		}
        		repaint();
            }
        });
        
        panel.add(btn);

        JButton btnClear = new JButton("Clear");
        btnClear.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		clearAlgo();
        	}
        });
        
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if(_graph_org.getMC()==_graph.getMC())return;
        		_graphAlgo.init(_graph_org);
        		_graph=_graphAlgo.copy();
        		clearAlgo();
        	}
        });
        panel.add(btnRefresh);
        panel.add(btnClear);
    }
    
    public void saveGraph(String fileName) {
    	if(_graphAlgo == null) {
    		return;
    	}
    	_graphAlgo.save(fileName);
    }
    
    public void loadGraph(String fileName) {
    	_graphAlgo = new Graph_Algo();
    	_graphAlgo.init(fileName);
    	_graph = _graphAlgo.copy(); 
    	repaint();
    }
    
    private void drawPoint(Graphics g, Point3D p, Color c) {
    	    g.setColor(c);
            g.fillOval((int) (scale * p.x()) - kRADIUS + offset, (int) p.y() * scale - kRADIUS + offset,
                    2 * kRADIUS, 2 * kRADIUS);

    }
    private void paintGraph(Graphics g) {
    	// Draw nodes
    	g.setColor(Color.RED);
    	for(node_data node : _graph.getV()) {
    		Color c = Color.BLUE;
    		if(node.getKey() == spSrc) {
    			c = Color.GREEN;
    		}
    		else if(node.getKey() == spDest) {
    			c = Color.PINK;	
    		}
    		drawPoint(g, node.getLocation(), c);
    		
    		// Draw edges

    		g.setColor(Color.YELLOW);
    		for(edge_data edge : _graph.getE(node.getKey())) {
    			
    			node_data src = _graph.getNode(edge.getSrc());
    			node_data dest = _graph.getNode(edge.getDest());
    			Point3D srcPoint = src.getLocation();
    			Point3D destPoint = dest.getLocation();
    			g.drawLine((int)(srcPoint.x() * scale) + offset, (int) (srcPoint.y() * scale )+ offset,
    					(int) (destPoint.x() * scale )+ offset, (int)( destPoint.y() * scale )+ offset);
    		}// Draw the arrow
    	}for(node_data node : _graph.getV()) {
    		for(edge_data edge : _graph.getE(node.getKey())) {
    			node_data src = _graph.getNode(edge.getSrc());
    			node_data dest = _graph.getNode(edge.getDest());
    			Point3D srcPoint = src.getLocation();
    			Point3D destPoint = dest.getLocation();
    			Point3D dir = new Point3D(destPoint);
    			dir.factor(-1);
    			dir.add(srcPoint);
    			dir.factor(0.15);
    			dir.add(destPoint);
    			g.setColor(Color.RED);
    			Point3D arrow1 = new Point3D(dir);

    			g.drawLine((int) (destPoint.x() * scale) + offset, (int) (destPoint.y() * scale) + offset,
    					(int) (arrow1.x() * scale) + offset, (int)( arrow1.y() * scale) + offset);

    			g.setColor(Color.black);

                 Font font = g.getFont().deriveFont(15.0f);
                 g.setFont(font);
                 double xText = scale * (srcPoint.x() + arrow1.x()) / 2 + offset;
                 double yText = scale * (srcPoint.y() + arrow1.y()) / 2 + offset;
                 g.drawString(String.format("%.2f", edge.getWeight()), (int) xText, (int) yText);
    		}	
    	}
    	
    	g.setColor(Color.blue);
    	
    		Point3D prev = null;
    		if(_shortestPath != null) {
    			for(node_data node : _shortestPath) {
    				if(prev != null) {
    					 Graphics2D g2 = (Graphics2D) g;
    		                g2.setStroke(new BasicStroke(2));
    		                g2.draw(new Line2D.Float((int) (prev.x() * scale )+ offset, (int)( prev.y() * scale) + offset,
    							(int)( node.getLocation().x() * scale) + offset, (int) (node.getLocation().y() * scale) + offset));
    				}
    				prev = node.getLocation();
    			}
    		}
    		
    		for(int tspTarget : _tspTargets) {
    			node_data node = _graph.getNode(tspTarget);
    			drawPoint(g, node.getLocation(), Color.GREEN);
    		}


    }
    
    public void paint(Graphics g) {
        super.paint(g);
        
        if(_graph != null) {
        	paintGraph(g);
        	return;
        }
        
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String str = e.getActionCommand();
        if (str.equals("open")) {
        	JFileChooser fileChooser = new JFileChooser();

        	int result = fileChooser.showOpenDialog(this);
        	if (result == JFileChooser.APPROVE_OPTION) {
        		File selectedFile = fileChooser.getSelectedFile();
        		System.out.println("Selected file: " + selectedFile.getAbsolutePath());
        		loadGraph(selectedFile.getAbsolutePath());
        	}
        }
        else if (str.equals("save as")) {
        	JFileChooser fileChooser = new JFileChooser();
        	fileChooser.setDialogTitle("Specify a file to save");   
        	 
        	int userSelection = fileChooser.showSaveDialog(this);
        	 
        	if (userSelection == JFileChooser.APPROVE_OPTION) {
        	    File fileToSave = fileChooser.getSelectedFile();
        	    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
        	    saveGraph(fileToSave.getAbsolutePath());
        	}
        }
    }
    
    node_data getClosestNode(int x, int y) {
    	int minDistanceToMatch = 5;
    	node_data closestNode = null;
    	double closestDistance = Double.POSITIVE_INFINITY;
    	
    	Point3D point = new Point3D(x, y, 0);
    	for(node_data node : _graph.getV()) {
    		double nodeDistance = node.getLocation().distance2D(point);
    		if((nodeDistance < minDistanceToMatch) &&
    				(nodeDistance < closestDistance)) {
    			closestNode = node;
    			closestDistance = nodeDistance;
    		}
    	}
    	return closestNode;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        int x = (e.getX() - offset) / scale;
        int y = (e.getY() - offset) / scale;

        node_data closestNode = getClosestNode(x, y);
        if(closestNode == null) {
        	return;
        }
        if(combobox.getSelectedItem() == "Shortest Path") {
        	if(e.getButton() == MouseEvent.BUTTON1) {
        		spSrc = closestNode.getKey();
        	}
        	else if(e.getButton() == MouseEvent.BUTTON3) {	
        		spDest = closestNode.getKey();
        	}
        }
        else {
        	_tspTargets.add(closestNode.getKey());
        }
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }
}
