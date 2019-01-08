package frame;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import controller.DrawingController;
import model.DrawingModel;
import strategy.FileDraw;
import strategy.FileLog;
import strategy.FilePicture;
import view.DrawingView;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

/**
 * 
 * @author Vukan Markovic
 */
public class DrawingFrame extends JFrame implements PropertyChangeListener {
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private final ButtonGroup buttonsGroup;
	private DrawingView view;
	private DrawingController controller;
	private Color color = Color.BLACK;
	private JToggleButton tglBtnSelect;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JButton btnUndo;
	private JButton btnRedo;
	private JButton btnToFront;
	private JButton btnToBack;
	private JButton btnBringToFront;
	private JButton btnBringToBack;
	private JButton btnNewDraw;
	private JButton btnSaveLog;
	private JButton btnSaveDrawing;
	private JButton btnSaveDrawingAsImage;
	private JButton btnLog;
	private JLabel mouseCoordinates;
	private MouseAdapter mouseAdapterUpdate;
	private MouseAdapter mouseAdapterDelete;
	private MouseAdapter mouseAdapterUndo;
	private MouseAdapter mouseAdapterRedo;
	private MouseAdapter mouseAdapterNewDraw;
	private MouseAdapter mouseAdapterSaveLog;
	private MouseAdapter mouseAdapterSaveDrawing;
	private MouseAdapter mouseAdapterSaveDrawingAsImage;
	private MouseAdapter mouseAdapterLog;
	private MouseAdapter mouseAdapterToFront;
	private MouseAdapter mouseAdapterToBack;
	private MouseAdapter mouseAdapterBringToFront;
	private MouseAdapter mouseAdapterBringToBack;
	private JList<String> activityLog;
	private DefaultListModel <String> dlmList;
	private JScrollPane scrollPane;
	
	/**
	 * 
	 * @param arrayOfStrings
	 */
	public static void main(String[] arrayOfStrings) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DrawingFrame frame = new DrawingFrame();
					frame.setVisible(true);
					frame.setTitle("Markovic Vukan IT20/2016");
					DrawingModel model = new DrawingModel();
					frame.getView().setModel(model);
					frame.setController(new DrawingController(model, frame));
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}
		});
	}

	public DrawingFrame() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage(DrawingFrame.class.getResource("/icons/cursor.png"));
		Image image1 = toolkit.getImage(DrawingFrame.class.getResource("/icons/hand.png"));
		setCursor(toolkit.createCustomCursor(image , new java.awt.Point(this.getX(), this.getY()), "img"));
		setForeground(Color.BLUE);
		setBackground(Color.CYAN);
		setIconImage(Toolkit.getDefaultToolkit().getImage(DrawingFrame.class.getResource("/icons/watercolor.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setLocationRelativeTo(null);
		
		mainPanel = new JPanel();
		mainPanel.setBackground(Color.BLUE);
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainPanel.setLayout(new BorderLayout(0, 0));
		setContentPane(mainPanel);
		dlmList = new DefaultListModel<String>();
		scrollPane = new JScrollPane();

		JPanel buttonsPanelForDrawing = new JPanel();
		buttonsPanelForDrawing.setBackground(Color.CYAN);
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBackground(Color.CYAN);

		view = new DrawingView();
		view.setBackground(Color.YELLOW);
		mainPanel.add(buttonsPanel, BorderLayout.NORTH);
		mainPanel.add(buttonsPanelForDrawing, BorderLayout.SOUTH);
		mainPanel.add(view, BorderLayout.CENTER);
		buttonsGroup = new ButtonGroup();

		JToggleButton tglBtnDrawPoint = new JToggleButton(new ImageIcon(DrawingFrame.class.getResource("/icons/icon.png")));
		tglBtnDrawPoint.setHorizontalTextPosition(SwingConstants.RIGHT);
		tglBtnDrawPoint.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		tglBtnDrawPoint.setFont(new Font("Dotum", Font.BOLD, 12));
		tglBtnDrawPoint.setText("Point");
		tglBtnDrawPoint.setBackground(Color.GREEN);
		buttonsGroup.add(tglBtnDrawPoint);
		tglBtnDrawPoint.setCursor(toolkit.createCustomCursor(image1 , new java.awt.Point(tglBtnDrawPoint.getX(), tglBtnDrawPoint.getY()), "img"));
		buttonsPanelForDrawing.add(tglBtnDrawPoint);

		JToggleButton tglBtnDrawSquare = new JToggleButton(new ImageIcon(DrawingFrame.class.getResource("/icons/check-box-empty.png")));
		tglBtnDrawSquare.setHorizontalTextPosition(SwingConstants.RIGHT);
		tglBtnDrawSquare.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		tglBtnDrawSquare.setFont(new Font("Dotum", Font.BOLD, 12));
		tglBtnDrawSquare.setText("Square");
		tglBtnDrawSquare.setBackground(Color.ORANGE);
		buttonsGroup.add(tglBtnDrawSquare);
		tglBtnDrawSquare.setCursor(toolkit.createCustomCursor(image1 , new java.awt.Point(tglBtnDrawSquare.getX(), tglBtnDrawSquare.getY()), "img"));
		buttonsPanelForDrawing.add(tglBtnDrawSquare);

		JToggleButton tglBtnDrawRectangle = new JToggleButton(new ImageIcon(DrawingFrame.class.getResource("/icons/photo-frame.png")));
		tglBtnDrawRectangle.setHorizontalTextPosition(SwingConstants.RIGHT);
		tglBtnDrawRectangle.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		tglBtnDrawRectangle.setFont(new Font("Dotum", Font.BOLD, 12));
		tglBtnDrawRectangle.setText("Rectangle");
		tglBtnDrawRectangle.setBackground(Color.PINK);
		buttonsGroup.add(tglBtnDrawRectangle);
		tglBtnDrawRectangle.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		buttonsPanelForDrawing.add(tglBtnDrawRectangle);

		JToggleButton tglBtnDrawLine = new JToggleButton(new ImageIcon(DrawingFrame.class.getResource("/icons/substract.png")));
		tglBtnDrawLine.setHorizontalTextPosition(SwingConstants.RIGHT);
		tglBtnDrawLine.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		tglBtnDrawLine.setFont(new Font("Dotum", Font.BOLD, 12));
		tglBtnDrawLine.setText("Line");
		tglBtnDrawLine.setBackground(new Color(153, 50, 204));
		buttonsGroup.add(tglBtnDrawLine);
		tglBtnDrawLine.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		buttonsPanelForDrawing.add(tglBtnDrawLine);

		JToggleButton tglBtnDrawCircle = new JToggleButton(new ImageIcon(DrawingFrame.class.getResource("/icons/circle.png")));
		tglBtnDrawCircle.setHorizontalTextPosition(SwingConstants.RIGHT);
		tglBtnDrawCircle.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		tglBtnDrawCircle.setFont(new Font("Dotum", Font.BOLD, 12));
		tglBtnDrawCircle.setText("Circle");
		tglBtnDrawCircle.setBackground(Color.RED);
		buttonsGroup.add(tglBtnDrawCircle);
		tglBtnDrawCircle.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		buttonsPanelForDrawing.add(tglBtnDrawCircle);
		
		JToggleButton tglBtnDrawHexagon = new JToggleButton(new ImageIcon(DrawingFrame.class.getResource("/icons/hexagon.png")));
		tglBtnDrawHexagon.setHorizontalTextPosition(SwingConstants.RIGHT);
		tglBtnDrawHexagon.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		tglBtnDrawHexagon.setFont(new Font("Dotum", Font.BOLD, 12));
		tglBtnDrawHexagon.setText("Hexagon");
		tglBtnDrawHexagon.setBackground(Color.MAGENTA);
		buttonsGroup.add(tglBtnDrawHexagon);
		tglBtnDrawHexagon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		buttonsPanelForDrawing.add(tglBtnDrawHexagon);
		
		btnSaveLog = new JButton(new ImageIcon(DrawingFrame.class.getResource("/icons/log.png")));
		btnSaveLog.setEnabled(false);
		btnSaveLog.setBackground(new Color(154, 205, 50));
		btnSaveLog.setText("Save log");
		btnSaveLog.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		buttonsPanelForDrawing.add(btnSaveLog);
		
		btnSaveDrawingAsImage = new JButton(new ImageIcon(DrawingFrame.class.getResource("/icons/save-drawing.png")));
		btnSaveDrawingAsImage.setEnabled(false);
		btnSaveDrawingAsImage.setBackground(new Color(144, 238, 144));
		btnSaveDrawingAsImage.setText("Save as picture");
		btnSaveDrawingAsImage.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		buttonsPanelForDrawing.add(btnSaveDrawingAsImage);
		
		btnSaveDrawing = new JButton(new ImageIcon(DrawingFrame.class.getResource("/icons/save.png")));
		btnSaveDrawing.setEnabled(false);
		btnSaveDrawing.setBackground(new Color(255, 222, 173));
		btnSaveDrawing.setText("Save draw");
		btnSaveDrawing.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		buttonsPanelForDrawing.add(btnSaveDrawing);
		
		btnNewDraw = new JButton("New draw");
		btnNewDraw.setEnabled(false);
		btnNewDraw.setBackground(new Color(222, 184, 135));
		btnNewDraw.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewDraw.setEnabled(false);
		buttonsPanelForDrawing.add(btnNewDraw);
		
		JButton btnOpenDrawLog = new JButton("Open draw/log");
		btnOpenDrawLog.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		buttonsPanelForDrawing.add(btnOpenDrawLog);
		mouseCoordinates = new JLabel("X:0, Y:0");
		buttonsPanelForDrawing.add(mouseCoordinates);
		
		btnLog = new JButton("Log");
		btnLog.setForeground(Color.RED);
		btnLog.setFont(new Font("Lucida Console", Font.BOLD, 12));
		btnLog.setBackground(Color.ORANGE);
		btnLog.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		buttonsPanel.add(btnLog);

		tglBtnSelect = new JToggleButton(new ImageIcon(DrawingFrame.class.getResource("/icons/select.png")));
		tglBtnSelect.setBackground(Color.YELLOW);
		tglBtnSelect.setEnabled(false);
		buttonsGroup.add(tglBtnSelect);
		tglBtnSelect.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		buttonsPanel.add(tglBtnSelect);

		btnUpdate = new JButton(new ImageIcon(DrawingFrame.class.getResource("/icons/loop-arrow.png")));
		btnUpdate.setBackground(Color.MAGENTA);
		btnUpdate.setEnabled(false);
		buttonsGroup.add(btnUpdate);
		btnUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		buttonsPanel.add(btnUpdate);

		btnDelete = new JButton(new ImageIcon(DrawingFrame.class.getResource("/icons/delete.png")));
		btnDelete.setBackground(Color.DARK_GRAY);
		btnDelete.setEnabled(false);
		buttonsGroup.add(btnDelete);
		btnDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		buttonsPanel.add(btnDelete);

		JButton btnEdgeColor = new JButton(new ImageIcon(DrawingFrame.class.getResource("/icons/picker.png")));
		btnEdgeColor.setForeground(new Color(0, 0, 204));
		btnEdgeColor.setText("Edge color");
		btnEdgeColor.setBackground(Color.BLACK);
		btnEdgeColor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		buttonsPanel.add(btnEdgeColor);

		JButton btnInteriorColor = new JButton(new ImageIcon(DrawingFrame.class.getResource("/icons/color-picker.png")));
		btnInteriorColor.setText("Area color");
		btnInteriorColor.setBackground(Color.WHITE);
		btnInteriorColor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		buttonsPanel.add(btnInteriorColor);
		
		btnUndo = new JButton(new ImageIcon(DrawingFrame.class.getResource("/icons/undo.png")));
		btnUndo.setBackground(Color.RED);
		btnUndo.setEnabled(false);
		btnUndo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		buttonsPanel.add(btnUndo);
		
		btnRedo = new JButton(new ImageIcon(DrawingFrame.class.getResource("/icons/redo.png")));
		btnRedo.setBackground(Color.ORANGE);
		btnRedo.setEnabled(false);
		btnRedo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		buttonsPanel.add(btnRedo);
		
		btnToFront = new JButton("ToFront");
		btnToFront.setForeground(Color.RED);
		btnToFront.setEnabled(false);
		btnToFront.setFont(new Font("Lucida Console", Font.BOLD, 11));
		btnToFront.setBackground(Color.GREEN);
		btnToFront.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		buttonsPanel.add(btnToFront);
		
		btnToBack = new JButton("ToBack");
		btnToBack.setFont(new Font("Lucida Console", Font.BOLD, 11));
		btnToBack.setEnabled(false);
		btnToBack.setForeground(Color.RED);
		btnToBack.setBackground(Color.BLUE);
		btnToBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		buttonsPanel.add(btnToBack);
		
		btnBringToFront = new JButton("BringToFront");
		btnBringToFront.setForeground(Color.RED);
		btnBringToFront.setEnabled(false);
		btnBringToFront.setFont(new Font("Lucida Console", Font.BOLD, 11));
		btnBringToFront.setBackground(Color.PINK);
		btnBringToFront.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		buttonsPanel.add(btnBringToFront);
		
		btnBringToBack = new JButton("BringToBack");
		btnBringToBack.setEnabled(false);
		btnBringToBack.setForeground(Color.RED);
		btnBringToBack.setFont(new Font("Lucida Console", Font.BOLD, 11));
		btnBringToBack.setBackground(Color.ORANGE);
		btnBringToBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		buttonsPanel.add(btnBringToBack);
		
		activityLog = new JList<String>();
		activityLog.setEnabled(false);
		activityLog.setModel(dlmList);
		activityLog.setBackground(Color.ORANGE);
		activityLog.setFont(new Font("Lucida Console", Font.BOLD, 12));
		scrollPane.setViewportView(activityLog);

		mouseAdapterUpdate = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent click) {
				controller.updateShapeClicked();
			}
		};
		
		mouseAdapterDelete = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent click) {
				controller.btnDeleteShapeClicked();
			}
		};
		
		mouseAdapterUndo = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent click) {
				controller.undo();
			}
		};
		
		mouseAdapterRedo = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent click) {
				controller.redo();
			}
		};
		
		mouseAdapterNewDraw = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent click) {
				controller.newDraw();
			}
		};
		
		mouseAdapterBringToBack = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent click) {
				controller.bringToBack();
			}
		};
		
		mouseAdapterBringToFront = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent click) {
				controller.bringToFront();
			}
		};
		
		mouseAdapterToBack = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent click) {
				controller.toBack();
			}
		};
		
		mouseAdapterToFront = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent click) {
				controller.toFront();
			}
		};
		
		mouseAdapterSaveDrawing = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent click) {
				controller.save(new FileDraw(getView().getModel()));
			}
		};

		mouseAdapterSaveDrawingAsImage = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent click) {
				controller.save(new FilePicture(getFrame()));
			}
		};
		
		mouseAdapterSaveLog = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent click) {
				controller.save(new FileLog(getFrame()));
			}
		};
		
		mouseAdapterLog = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent click) {
				if (btnLog.getText().equals("Log")) {
					mainPanel.remove(view);
					mainPanel.add(scrollPane, BorderLayout.CENTER);
					btnLog.setText("Draw");
					
				} else if (btnLog.getText().equals("Draw")) {
					mainPanel.remove(scrollPane);
					mainPanel.add(view, BorderLayout.CENTER);
					btnLog.setText("Log");
				}
				
				repaint();
			}
		};
		
		btnEdgeColor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent click) {
				color = controller.btnEdgeColorClicked();
				if (color != null) btnEdgeColor.setBackground(color);
			}
		});

		btnInteriorColor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent click) {
				color = controller.btnInteriorColorClicked();
				if (color != null) btnInteriorColor.setBackground(color);
			}
		});
		
		btnOpenDrawLog.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.open();
			}
		});
	
		view.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent click) {
				if (tglBtnDrawPoint.isSelected()) controller.btnAddPointClicked(click);
				else if (tglBtnDrawLine.isSelected()) controller.btnAddLineClicked(click);
				else if (tglBtnDrawSquare.isSelected()) controller.btnAddSquareClicked(click);
				else if (tglBtnDrawRectangle.isSelected()) controller.btnAddRectangleClicked(click);
				else if (tglBtnDrawCircle.isSelected()) controller.btnAddCircleClicked(click);
				else if (tglBtnDrawHexagon.isSelected()) controller.btnAddHexagonClicked(click);
				if (tglBtnSelect.isSelected()) controller.btnSelectShapeClicked(click);
			}
		});
		
		view.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				mouseCoordinates.setText("X:" + e.getX() + ", Y:" + e.getY());
			}
		});
	}

	/**
	 * 
	 * @param controller
	 */
	public void setController(DrawingController controller) {
		this.controller = controller;
		controller.addPropertyChangedListener(this);
	}

	/**
	 * 
	 * @return
	 */
	public DrawingView getView() {
		return view;
	}
	
	public void addListener(JButton button, MouseAdapter adapter) {
		button.setEnabled(true);
		button.addMouseListener(adapter);
	}
	
	public void removeListener(JButton button, MouseAdapter adapter) {
		button.removeMouseListener(adapter);
		button.setEnabled(false);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("shape selected")) {
			addListener(btnDelete, mouseAdapterDelete);
		} else if (evt.getPropertyName().equals("shape exist")) {
			tglBtnSelect.setEnabled(true);
			addListener(btnNewDraw, mouseAdapterNewDraw);
			addListener(btnUndo, mouseAdapterUndo);
			addListener(btnSaveDrawing, mouseAdapterSaveDrawing);
			addListener(btnSaveLog, mouseAdapterSaveLog);
			addListener(btnSaveDrawingAsImage, mouseAdapterSaveDrawingAsImage);
			addListener(btnLog, mouseAdapterLog);
		}
		else if (evt.getPropertyName().equals("shape unselected") || evt.getPropertyName().equals("shape don't exist")) {
			removeListener(btnUpdate, mouseAdapterUpdate);
			removeListener(btnDelete, mouseAdapterDelete);
		} else if (evt.getPropertyName().equals("shape don't exist") || evt.getPropertyName().equals("undo turn off")) {
			tglBtnSelect.setEnabled(false);
			removeListener(btnNewDraw, mouseAdapterNewDraw);
			removeListener(btnUndo, mouseAdapterUndo);
			removeListener(btnSaveDrawing, mouseAdapterSaveDrawing);
			removeListener(btnSaveLog, mouseAdapterSaveLog);
			removeListener(btnSaveDrawingAsImage, mouseAdapterSaveDrawingAsImage);
			removeListener(btnLog, mouseAdapterLog);
			removeListener(btnToBack, mouseAdapterToBack);
			removeListener(btnToFront, mouseAdapterToFront);
			removeListener(btnBringToBack, mouseAdapterBringToBack);
			removeListener(btnBringToFront, mouseAdapterBringToFront);
		}
		else if (evt.getPropertyName().equals("update turn off")) removeListener(btnUpdate, mouseAdapterUpdate);
		else if (evt.getPropertyName().equals("update turn on")) addListener(btnUpdate, mouseAdapterUpdate);
		else if (evt.getPropertyName().equals("redo turn off")) removeListener(btnRedo, mouseAdapterRedo);
		else if (evt.getPropertyName().equals("redo turn on")) addListener(btnRedo, mouseAdapterRedo);
		else if (evt.getPropertyName().equals("undo turn on")) addListener(btnUndo, mouseAdapterUndo);
		else if (evt.getPropertyName().equals("change position turn on")) {
			addListener(btnToBack, mouseAdapterToBack);
			addListener(btnToFront, mouseAdapterToFront);
			addListener(btnBringToBack, mouseAdapterBringToBack);
			addListener(btnBringToFront, mouseAdapterBringToFront);
		}
	}
	
	public DefaultListModel<String> getList() {
		return dlmList;
	}
	
	public DrawingFrame getFrame() {
		return this;
	}
}