package com.weakie.desktop.view;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CellEditor.LayoutData;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationEvent;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TableViewerEditor;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.wb.swt.SWTResourceManager;

import swing2swt.layout.BorderLayout;

import com.weakie.desktop.view.util.DataFormat;
import com.weakie.share.bean.Point3D;
import com.weakie.share.bean.Speed;
import com.weakie.share.jni.SendData;

import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.swt.custom.TableTree;
import org.eclipse.jface.viewers.TableTreeViewer;
import org.eclipse.jface.layout.TableColumnLayout;
import swing2swt.layout.FlowLayout;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.layout.FillLayout;
import swing2swt.layout.BoxLayout;

public class MainView {

	protected Shell shell;
	private Text text_2;
	private Text text_3;
	private Text text_5;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Composite composite;
	private Spinner spinner;
	private Spinner spinner_1;
	private Spinner spinner_2;
	private Table table;
	private ToolItem tltmCopy;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		Display display = Display.getDefault();
		Realm.runWithDefault(SWTObservables.getRealm(display), new Runnable() {
			public void run() {
				try {
					MainView window = new MainView();
					window.open();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setMinimumSize(new Point(822, 600));
		shell.setModified(true);
		shell.setSize(822, 604);
		shell.setText("SWT Application");
		shell.setLayout(new BorderLayout(0, 0));
		
		composite = new Composite(shell, SWT.BORDER | SWT.NO_RADIO_GROUP);
		composite.setLayoutData(BorderLayout.WEST);
		composite.setLayout(new RowLayout(SWT.VERTICAL));
		
		ExpandableComposite xpndblcmpstcom = formToolkit.createExpandableComposite(composite, ExpandableComposite.TWISTIE);
		xpndblcmpstcom.setBackground(SWTResourceManager.getColor(211, 211, 211));
		xpndblcmpstcom.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent arg0) {
				composite.layout();
			}
		});
		formToolkit.paintBordersFor(xpndblcmpstcom);
		xpndblcmpstcom.setText("\u914D\u7F6E\u7AEF\u53E3\u53C2\u6570");
		xpndblcmpstcom.setExpanded(true);
		
		Group group = new Group(xpndblcmpstcom, SWT.NONE);
		xpndblcmpstcom.setClient(group);
		group.setFont(SWTResourceManager.getFont("\u5FAE\u8F6F\u96C5\u9ED1", 9, SWT.NORMAL));
		group.setText("\u521D\u59CB\u5316\u53C2\u6570");
		RowLayout rl_group = new RowLayout(SWT.VERTICAL);
		rl_group.marginLeft = 5;
		rl_group.wrap = false;
		rl_group.spacing = 1;
		rl_group.marginWidth = 3;
		rl_group.marginTop = 5;
		rl_group.marginRight = 2;
		rl_group.marginBottom = 5;
		rl_group.justify = true;
		rl_group.center = true;
		rl_group.fill = true;
		group.setLayout(rl_group);
		
		Composite composite_4 = new Composite(group, SWT.NONE);
		GridLayout gl_composite_4 = new GridLayout(2, false);
		gl_composite_4.marginHeight = 2;
		composite_4.setLayout(gl_composite_4);
		
		Label label = new Label(composite_4, SWT.NONE);
		label.setText("\u7AEF\u53E3\u53F7");
		
		Combo combo = new Combo(composite_4, SWT.NONE);
		combo.setItems(new String[] {"1", "2", "3", "4"});
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(combo);
		formToolkit.paintBordersFor(combo);
		combo.select(0);
		
		Composite composite_5 = new Composite(group, SWT.NONE);
		GridLayout gl_composite_5 = new GridLayout(2, false);
		gl_composite_5.marginHeight = 2;
		composite_5.setLayout(gl_composite_5);
		
		Label label_1 = new Label(composite_5, SWT.NONE);
		label_1.setText("\u6CE2\u7279\u7387");
		
		Combo combo_1 = new Combo(composite_5, SWT.NONE);
		combo_1.setItems(new String[] {"1", "2", "3", "4"});
		combo_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Composite composite_6 = new Composite(group, SWT.NONE);
		GridLayout gl_composite_6 = new GridLayout(2, false);
		gl_composite_6.marginHeight = 2;
		composite_6.setLayout(gl_composite_6);
		
		Label label_2 = new Label(composite_6, SWT.NONE);
		label_2.setText("\u6570\u636E\u4F4D");
		
		Combo combo_2 = new Combo(composite_6, SWT.NONE);
		combo_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Composite composite_7 = new Composite(group, SWT.NONE);
		GridLayout gl_composite_7 = new GridLayout(2, false);
		gl_composite_7.marginHeight = 2;
		composite_7.setLayout(gl_composite_7);
		
		Label label_3 = new Label(composite_7, SWT.NONE);
		label_3.setText("\u505C\u6B62\u4F4D");
		
		Combo combo_3 = new Combo(composite_7, SWT.NONE);
		combo_3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Composite composite_8 = new Composite(group, SWT.NONE);
		GridLayout gl_composite_8 = new GridLayout(2, false);
		gl_composite_8.marginHeight = 2;
		composite_8.setLayout(gl_composite_8);
		
		Label label_4 = new Label(composite_8, SWT.NONE);
		label_4.setText("\u68C0\u9A8C\u4F4D");
		
		Combo combo_4 = new Combo(composite_8, SWT.NONE);
		combo_4.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		ExpandableComposite expandableComposite_1 = formToolkit.createExpandableComposite(composite, ExpandableComposite.TWISTIE);
		expandableComposite_1.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent arg0) {
				composite.layout();
			}
		});
		expandableComposite_1.setBackground(SWTResourceManager.getColor(211, 211, 211));
		formToolkit.paintBordersFor(expandableComposite_1);
		expandableComposite_1.setText("\u8BBE\u7F6E\u76EE\u6807\u4F4D\u7F6E");
		expandableComposite_1.setExpanded(true);
		
		Group group_1 = new Group(expandableComposite_1, SWT.NONE);
		expandableComposite_1.setClient(group_1);
		group_1.setText("\u76EE\u6807\u4F4D\u7F6E");
		RowLayout rl_group_1 = new RowLayout(SWT.VERTICAL);
		rl_group_1.spacing = 1;
		rl_group_1.marginWidth = 3;
		rl_group_1.marginTop = 5;
		rl_group_1.marginRight = 2;
		rl_group_1.marginLeft = 5;
		rl_group_1.marginBottom = 5;
		rl_group_1.justify = true;
		rl_group_1.center = true;
		rl_group_1.fill = true;
		group_1.setLayout(rl_group_1);
		
		Composite composite_2 = new Composite(group_1, SWT.NONE);
		GridLayout gl_composite_2 = new GridLayout(2, false);
		gl_composite_2.marginHeight = 2;
		composite_2.setLayout(gl_composite_2);
		
		Label lblX = new Label(composite_2, SWT.NONE);
		lblX.setText("X\u5750\u6807");
		
		spinner = new Spinner(composite_2, SWT.BORDER);
		spinner.setFont(SWTResourceManager.getFont("Courier New", 9, SWT.NORMAL));
		spinner.setMaximum(10000000);
		spinner.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(spinner);
		formToolkit.paintBordersFor(spinner);
		
		Composite composite_3 = new Composite(group_1, SWT.NONE);
		GridLayout gl_composite_3 = new GridLayout(2, false);
		gl_composite_3.marginHeight = 2;
		composite_3.setLayout(gl_composite_3);
		composite_3.setLayoutData(new RowData(139, SWT.DEFAULT));
		
		Label lblY = new Label(composite_3, SWT.NONE);
		lblY.setText("Y\u5750\u6807");
		
		spinner_1 = new Spinner(composite_3, SWT.BORDER);
		spinner_1.setFont(SWTResourceManager.getFont("Courier New", 9, SWT.NORMAL));
		spinner_1.setMaximum(10000000);
		spinner_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(spinner_1);
		formToolkit.paintBordersFor(spinner_1);
		
		Composite composite_12 = new Composite(group_1, SWT.NONE);
		GridLayout gl_composite_12 = new GridLayout(2, false);
		gl_composite_12.marginHeight = 2;
		composite_12.setLayout(gl_composite_12);
		
		Label lblZ = new Label(composite_12, SWT.NONE);
		lblZ.setText("Z\u5750\u6807");
		
		spinner_2 = new Spinner(composite_12, SWT.BORDER);
		spinner_2.setFont(SWTResourceManager.getFont("Courier New", 9, SWT.NORMAL));
		spinner_2.setMaximum(10000000);
		spinner_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(spinner_2);
		formToolkit.paintBordersFor(spinner_2);
		
		ExpandableComposite expandableComposite_2 = formToolkit.createExpandableComposite(composite, ExpandableComposite.TWISTIE);
		expandableComposite_2.addExpansionListener(new ExpansionAdapter() {
			public void expansionStateChanged(ExpansionEvent arg0) {
				composite.layout();
			}
		});
		expandableComposite_2.setBackground(SWTResourceManager.getColor(211, 211, 211));
		formToolkit.paintBordersFor(expandableComposite_2);
		expandableComposite_2.setText("\u8BBE\u7F6E\u79FB\u52A8\u901F\u5EA6");
		expandableComposite_2.setExpanded(true);
		
		Group group_2 = new Group(expandableComposite_2, SWT.NONE);
		expandableComposite_2.setClient(group_2);
		group_2.setText("\u79FB\u52A8\u901F\u5EA6");
		RowLayout rl_group_2 = new RowLayout(SWT.VERTICAL);
		rl_group_2.spacing = 1;
		rl_group_2.marginWidth = 3;
		rl_group_2.marginTop = 5;
		rl_group_2.marginRight = 2;
		rl_group_2.marginLeft = 5;
		rl_group_2.marginBottom = 5;
		rl_group_2.center = true;
		rl_group_2.justify = true;
		rl_group_2.fill = true;
		group_2.setLayout(rl_group_2);
		
		Composite composite_9 = new Composite(group_2, SWT.NONE);
		GridLayout gl_composite_9 = new GridLayout(2, false);
		gl_composite_9.marginHeight = 2;
		composite_9.setLayout(gl_composite_9);
		composite_9.setLayoutData(new RowData(138, SWT.DEFAULT));
		
		Label lblX_1 = new Label(composite_9, SWT.NONE);
		lblX_1.setText("X\u901F\u5EA6");
		
		text_2 = new Text(composite_9, SWT.BORDER | SWT.RIGHT);
		text_2.setFont(SWTResourceManager.getFont("Courier New", 9, SWT.NORMAL));
		text_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		
		Composite composite_10 = new Composite(group_2, SWT.NONE);
		GridLayout gl_composite_10 = new GridLayout(2, false);
		gl_composite_10.marginHeight = 2;
		composite_10.setLayout(gl_composite_10);
		
		Label lblY_1 = new Label(composite_10, SWT.NONE);
		lblY_1.setText("Y\u901F\u5EA6");
		
		text_3 = new Text(composite_10, SWT.BORDER | SWT.RIGHT);
		text_3.setFont(SWTResourceManager.getFont("Courier New", 9, SWT.NORMAL));
		text_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		
		Composite composite_13 = new Composite(group_2, SWT.NONE);
		GridLayout gl_composite_13 = new GridLayout(2, false);
		gl_composite_13.marginHeight = 2;
		composite_13.setLayout(gl_composite_13);
		
		Label lblZ_1 = new Label(composite_13, SWT.NONE);
		lblZ_1.setText("Z\u901F\u5EA6");
		
		text_5 = new Text(composite_13, SWT.BORDER | SWT.RIGHT);
		text_5.setFont(SWTResourceManager.getFont("Courier New", 9, SWT.NORMAL));
		text_5.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1));
		
		Composite composite_11 = new Composite(composite, SWT.NONE);
		composite_11.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		Button button_1 = new Button(composite_11, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				SendData.getInstance().initCOM();
			}
		});
		formToolkit.adapt(button_1, true, true);
		button_1.setText("\u8FDE\u63A5\u7AEF\u53E3");
		
		Composite composite_14 = new Composite(composite, SWT.NONE);
		formToolkit.adapt(composite_14);
		formToolkit.paintBordersFor(composite_14);
		composite_14.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		Button button = new Button(composite_14, SWT.NONE);
		formToolkit.adapt(button, true, true);
		button.setText("\u7D27\u6025\u590D\u4F4D");
		button.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e){
				byte[] buf = new byte[32];
				try{
					SendData.getInstance().formateData(new Point3D(0, 0, 0), new Speed(20000, 20000, 20000), buf);
					SendData.getInstance().sendData(buf);
				}catch(Exception e2){
					e2.printStackTrace();
				}
				
			}
		});
		Button button_2 = new Button(composite_14, SWT.NONE);
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				byte[] buf = new byte[32];
				try{
					int x = Integer.parseInt(MainView.this.spinner.getText());
					int y = Integer.parseInt(MainView.this.spinner_1.getText());
					int z = Integer.parseInt(MainView.this.spinner_2.getText());
					int sx = Integer.parseInt(MainView.this.text_2.getText());
					int sy = Integer.parseInt(MainView.this.text_3.getText());
					int sz = Integer.parseInt(MainView.this.text_5.getText());
					System.out.println(x+" "+ y+" "+z+" "+sx+" "+sy+" "+sz);
					SendData.getInstance().formateData(new Point3D(x, y, z), new Speed(sx, sy, sz), buf);
					for(byte b:buf){
						System.out.print(b+" ");
					}
					System.out.println();
					SendData.getInstance().sendData(buf);
				}catch(Exception e2){
					e2.printStackTrace();
				}
				
			}
		});
		button_2.setText("\u79FB\u52A8");
		
		Composite composite_1 = new Composite(shell, SWT.NONE);
		composite_1.setLayout(new BorderLayout(0, 0));
		
		CTabFolder tabFolder = new CTabFolder(composite_1, SWT.BORDER | SWT.CLOSE | SWT.FLAT);
		tabFolder.setMinimized(true);
		tabFolder.setMinimizeVisible(true);
		tabFolder.setMaximized(true);
		tabFolder.setMaximizeVisible(true);
		tabFolder.setLayoutData(BorderLayout.CENTER);
		formToolkit.adapt(tabFolder);
		formToolkit.paintBordersFor(tabFolder);
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
		CTabItem tbtmNewFile = new CTabItem(tabFolder, SWT.CLOSE);
		tbtmNewFile.setText("new file");
		
		Composite composite_15 = new Composite(tabFolder, SWT.NONE);
		tbtmNewFile.setControl(composite_15);
		formToolkit.paintBordersFor(composite_15);
		composite_15.setLayout(new BorderLayout(0, 0));
		
		final TableViewer tableViewer = new TableViewer(composite_15, SWT.BORDER | SWT.CHECK | SWT.FULL_SELECTION | SWT.MULTI);
		table = tableViewer.getTable();
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setLayoutData(BorderLayout.CENTER);
		table.setLayout(new TableLayout());
		formToolkit.paintBordersFor(table);
		
		
		final TableEditor editor = new TableEditor(table);
	    editor.horizontalAlignment = SWT.CENTER;
	    editor.grabHorizontal = true;
	    table.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseDoubleClick(MouseEvent event) {
				//dispose old edit
				Control old = editor.getEditor();
				if (old != null)
					old.dispose();

				//get current table item
				Point pt = new Point(event.x, event.y);
				final TableItem item = table.getItem(pt);
				if (item == null) {
					return;
				}
				//get current column
				int column = -1;
				for (int i = 0, n = table.getColumnCount(); i < n; i++) {
					Rectangle rect = item.getBounds(i);
					if (rect.contains(pt)) {
						column = i;
						break;
					}
				}
				if (column == -1) {
					return;
				}
				//enable cell edit
				final Text text = new Text(table, (column != 2 ? SWT.NONE : SWT.RIGHT));
				text.setText(DataFormat.formatTextData(item.getText(column), column));
				text.setForeground(item.getForeground());
				text.selectAll();
				text.setFocus();
				
				editor.minimumWidth = text.getBounds().width;
				editor.setEditor(text, item, column);

				final int col = column;
				text.addModifyListener(new ModifyListener() {
					public void modifyText(ModifyEvent event) {
						String data = DataFormat.formatTableData(text.getText(), col);
						item.setText(col, data);
					}
				});
				
				text.addVerifyListener(new VerifyListener(){   
				    public void verifyText(VerifyEvent e) { 
				    	e.doit = DataFormat.verifyTableData(e.text, col);
				    }   
				});  
				
				text.addFocusListener(new FocusAdapter(){
					@Override
					public void focusLost(FocusEvent arg0) {
						String data = DataFormat.formatTableData(text.getText(), col);
						item.setText(col, data);
						text.dispose();
					}
				});

			}
	    });
	    
	    
	    
		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn = tableViewerColumn.getColumn();
		tblclmnNewColumn.setResizable(false);
		tblclmnNewColumn.setWidth(156);
		tblclmnNewColumn.setText("Point");
		
		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn_3 = tableViewerColumn_3.getColumn();
		tblclmnNewColumn_3.setResizable(false);
		tblclmnNewColumn_3.setWidth(160);
		tblclmnNewColumn_3.setText("Speed");
		
		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn_2 = tableViewerColumn_2.getColumn();
		tblclmnNewColumn_2.setResizable(false);
		tblclmnNewColumn_2.setAlignment(SWT.CENTER);
		tblclmnNewColumn_2.setWidth(100);
		tblclmnNewColumn_2.setText("Time");
		
		TableViewerColumn tableViewerColumn_5 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnStrategy = tableViewerColumn_5.getColumn();
		tblclmnStrategy.setWidth(100);
		tblclmnStrategy.setText("strategy");
		
		TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnStatus = tableViewerColumn_4.getColumn();
		tblclmnStatus.setAlignment(SWT.CENTER);
		tblclmnStatus.setResizable(false);
		tblclmnStatus.setWidth(55);
		tblclmnStatus.setText("status");
		
		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn_1 = tableViewerColumn_1.getColumn();
		tblclmnNewColumn_1.setResizable(false);
		tblclmnNewColumn_1.setWidth(224);
		tblclmnNewColumn_1.setText("Comment");
		
		TableItem item = new TableItem(table,SWT.LEFT);
		item.setText(new String[]{"(1,1,1)","(1,1,1)","2ms","ready"," "});
		
		Composite composite_16 = new Composite(composite_15, SWT.NONE);
		composite_16.setLayoutData(BorderLayout.NORTH);
		formToolkit.adapt(composite_16);
		formToolkit.paintBordersFor(composite_16);
		composite_16.setLayout(new BorderLayout(0, 0));
		
		ToolBar toolBar = new ToolBar(composite_16, SWT.FLAT | SWT.RIGHT);
		formToolkit.adapt(toolBar);
		formToolkit.paintBordersFor(toolBar);
		
		tltmCopy = new ToolItem(toolBar, SWT.CHECK);
		tltmCopy.setText("Copy");
		
		ToolItem tltmAddfirst = new ToolItem(toolBar, SWT.NONE);
		tltmAddfirst.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TableItem item = new TableItem(table,SWT.LEFT,0);
				item.setText(new String[]{"(1,1,1)","(1,1,1)","2ms"," "});
			}
		});
		tltmAddfirst.setText("AddFirst");
		
		ToolItem tltmAddlast = new ToolItem(toolBar, SWT.NONE);
		tltmAddlast.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int count = table.getItemCount();
				TableItem item = new TableItem(table,SWT.LEFT,count);
				item.setText(new String[]{"(1,1,1)","(1,1,1)","2ms"," "});
			}
		});
		tltmAddlast.setText("AddLast");
		
		ToolItem tltmAdd = new ToolItem(toolBar, SWT.NONE);
		tltmAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = tableViewer.getTable().getSelectionIndex();
				if(index == -1){
					index = 0;
				}
				TableItem item = new TableItem(table,SWT.LEFT,index);
				item.setText(new String[]{"(1,1,1)","(1,1,1)","2ms"," "});
			}
		});
		tltmAdd.setText("AddBefore");
		
		ToolItem tltmAddafter = new ToolItem(toolBar, SWT.NONE);
		tltmAddafter.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = tableViewer.getTable().getSelectionIndex();
				if(index == -1){
					index = table.getItemCount()-1;
				}
				TableItem item = new TableItem(table,SWT.LEFT,index+1);
				item.setText(new String[]{"(1,1,1)","(1,1,1)","2ms"," "});
			}
		});
		tltmAddafter.setText("AddAfter");
		
		ToolItem tltmDel = new ToolItem(toolBar, SWT.NONE);
		tltmDel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int[] index = table.getSelectionIndices();
				if(index.length==0){
					return;
				}
				table.remove(index);
			}
		});
		tltmDel.setText("Delete");
		
		Composite composite_17 = new Composite(composite_15, SWT.NONE);
		composite_17.setLayoutData(BorderLayout.SOUTH);
		formToolkit.adapt(composite_17);
		formToolkit.paintBordersFor(composite_17);
		GridLayout gl_composite_17 = new GridLayout(7, false);
		gl_composite_17.marginWidth = 2;
		gl_composite_17.marginTop = 2;
		gl_composite_17.marginHeight = 2;
		composite_17.setLayout(gl_composite_17);
		
		Button btnCheckButton = new Button(composite_17, SWT.CHECK);
		formToolkit.adapt(btnCheckButton, true, true);
		btnCheckButton.setText("Check Button");
		
		Button btnNewButton = new Button(composite_17, SWT.NONE);
		formToolkit.adapt(btnNewButton, true, true);
		btnNewButton.setText("RUN\r\n");
		
		Button btnNewButton_1 = new Button(composite_17, SWT.NONE);
		formToolkit.adapt(btnNewButton_1, true, true);
		btnNewButton_1.setText("CANCEL");
		new Label(composite_17, SWT.NONE);
		
		Label lblNewLabel = new Label(composite_17, SWT.NONE);
		formToolkit.adapt(lblNewLabel, true, true);
		lblNewLabel.setText("%");
		new Label(composite_17, SWT.NONE);
		
		ProgressBar progressBar = new ProgressBar(composite_17, SWT.NONE);
		GridData gd_progressBar = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_progressBar.widthHint = 274;
		progressBar.setLayoutData(gd_progressBar);
		formToolkit.adapt(progressBar, true, true);
		
		CTabItem tbtmFile = new CTabItem(tabFolder, SWT.NONE);
		tbtmFile.setText("file");
		
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("\u6587\u4EF6");
		
		Menu menu_1 = new Menu(mntmFile);
		mntmFile.setMenu(menu_1);

	}
}
