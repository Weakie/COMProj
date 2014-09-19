package com.weakie.desktop.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.forms.widgets.FormToolkit;

import swing2swt.layout.BorderLayout;

import com.weakie.desktop.bean.TablePropertieFactory;
import com.weakie.desktop.bean.propertie.ViewProperties;
import com.weakie.desktop.view.listener.DataModifyListener;
import com.weakie.share.control.ActionDispatcherControl;
import com.weakie.share.control.ProgressControl;
import com.weakie.share.control.bean.ActionCommand;
import com.weakie.share.control.gen.ActionGeneratorProducer;

public class TabEditView extends Composite {

	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	
	private Table table;
	private PropertiesView propertiesView;
	private List<ViewProperties> data = new ArrayList<ViewProperties>();
	
	private DataModifyListener listener = new DataModifyListener(){
		private int index = 0;
		private boolean active = false;
		
		@Override
		public synchronized void update(Map<String, String> properties) {
			if(!this.isAlive()){
				return;
			}
			TabEditView.this.updateTableData(index,properties);
		}
		
		@Override
		public synchronized boolean isAlive() {
			return this.active;
		}
		
		@Override
		public synchronized ViewProperties getProperties() {
			if(!this.isAlive()){
				return null;
			}
			return data.get(index);
		}
		
		@Override
		public synchronized void disable() {
			this.active = false;
		}

		@Override
		public synchronized void enable(int index) {
			this.index = index;
			this.active = true;
		}
	};
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TabEditView(Composite parent, int style) {
		super(parent, style);

		Composite composite_15 = this;
		
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
	    	public void mouseDown(MouseEvent event){
	    		int index = table.getSelectionIndex();
	    		if(index==-1){
	    			return;
	    		}
	    		listener.enable(index);
	    		propertiesView.setPropertiesListener(listener);
	    	}
	    	
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
				if (column == -1 || column != 0) {
					// statue is not editable
					return;
				}
				final int col = column;
				// enable cell edit
				final Combo combo = new Combo(table, SWT.NONE);
				Set<String> types = ActionGeneratorProducer.getGeneratorTypes();
				combo.setItems(types.toArray(new String[0]));
				combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
				combo.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						String data = combo.getText();
						item.setText(col, data);
						changeDataType(table.getSelectionIndex(), data);
					}
				});

				combo.addFocusListener(new FocusAdapter() {
					@Override
					public void focusLost(FocusEvent arg0) {
						item.setText(col, combo.getText());
						combo.dispose();
					}
				});
				
				editor.minimumWidth = combo.getBounds().width;
				editor.setEditor(combo, item, column);

			}
	    });
	    
	    TableViewerColumn tableViewerColumn_5 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnStrategy = tableViewerColumn_5.getColumn();
		tblclmnStrategy.setWidth(100);
		tblclmnStrategy.setText("Type");
		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn tblclmnNewColumn = tableViewerColumn.getColumn();
		tblclmnNewColumn.setResizable(false);
		tblclmnNewColumn.setWidth(156);
		tblclmnNewColumn.setText("TargetPoint");
		
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
		
		//TableItem item = new TableItem(table,SWT.LEFT);
		//item.setText(new String[]{"(1,1,1)","(1,1,1)","2ms","","ready"," "});
		
		Composite composite_16 = new Composite(composite_15, SWT.NONE);
		composite_16.setLayoutData(BorderLayout.NORTH);
		formToolkit.adapt(composite_16);
		formToolkit.paintBordersFor(composite_16);
		composite_16.setLayout(new BorderLayout(0, 0));
		
		ToolBar toolBar = new ToolBar(composite_16, SWT.FLAT | SWT.RIGHT);
		formToolkit.adapt(toolBar);
		formToolkit.paintBordersFor(toolBar);
		
		ToolItem tltmCopy = new ToolItem(toolBar, SWT.CHECK);
		tltmCopy.setText("Copy");
		
		ToolItem tltmAddfirst = new ToolItem(toolBar, SWT.NONE);
		tltmAddfirst.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				addNewTableData(0);
			}
		});
		tltmAddfirst.setText("AddFirst");
		
		ToolItem tltmAddlast = new ToolItem(toolBar, SWT.NONE);
		tltmAddlast.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int count = table.getItemCount();
				addNewTableData(count);
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
				addNewTableData(index);
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
				addNewTableData(index+1);
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
				removeTableData(index);
			}
		});
		tltmDel.setText("Delete");
		
		ToolItem tltmRefresh = new ToolItem(toolBar, SWT.NONE);
		tltmRefresh.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				showTableData();
			}
		});
		tltmRefresh.setText("Refresh");
		
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
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				List<ActionCommand> command = new ArrayList<ActionCommand>();
				for(ViewProperties vp : data){
					command.add(vp.createActionCommand());
				}
				ActionDispatcherControl.getInstance().executeCommands(command, new ProgressControl() {
					
					@Override
					public void update(int id, String value) {
						System.out.println("begin:"+id+",value:"+value);
					}
					
					@Override
					public void init(int size) {
						System.out.println("init size:"+size);
					}
					
					@Override
					public void end(int id) {
						System.out.println("end:"+id);
					}
					
					@Override
					public void close() {
						System.out.println("close");
					}
					
					@Override
					public void begin(int id) {
						System.out.println("begin:"+id);
					}
				});
			}
		});
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
		
		propertiesView = new PropertiesView(composite_15, SWT.NONE);
		propertiesView.setLayoutData(BorderLayout.EAST);
		formToolkit.adapt(propertiesView);
		formToolkit.paintBordersFor(propertiesView);
	}
	
	private synchronized void removeTableData(int[] index){
		table.remove(index);
		Arrays.sort(index);
		for(int i = index.length-1;i>=0;i--){
			data.remove(i);
		}
	}
	
	private synchronized void addNewTableData(int index){
		ViewProperties bean = TablePropertieFactory.createNewPropertiesBean();
		TableItem item = new TableItem(table,SWT.LEFT,index);
		item.setText(bean.getTableData());
		data.add(index, bean);
	}
	
	private synchronized void updateTableData(int index,Map<String,String> properties){
		ViewProperties bean = data.get(index);
		bean.updateProperties(properties);
		TableItem item = table.getItem(index);
		item.setText(bean.getTableData());
	}
	
	private void changeDataType(int index,String value){
		ViewProperties bean = TablePropertieFactory.createNewPropertiesBean(value);
		this.data.set(index, bean);
	}
	
	private synchronized void showTableData(){
		this.table.removeAll();
		for(ViewProperties bean:this.data){
			TableItem tableItem = new TableItem(table, SWT.NONE);
			tableItem.setText(bean.getTableData());
		}
	}
	
	//TODO
	public void save(){
		
	}
	public void load(){
		
	}
}
