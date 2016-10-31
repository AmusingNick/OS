package test;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class EditJTree {
	JFrame jf = new JFrame();
	JTree tree;
	DefaultTreeModel model ;  
	DefaultMutableTreeNode root ;
	DefaultMutableTreeNode guangdong ;
	DefaultMutableTreeNode guangxi ;
	DefaultMutableTreeNode foshan ;
	DefaultMutableTreeNode shantou ;
	DefaultMutableTreeNode guilin ;
	DefaultMutableTreeNode nanning;
	TreePath movePath ; 
	JButton addSiblingButton = new JButton("����ֵܽڵ�");
	JButton addChildButton = new JButton("����ӽڵ�");
	JButton deleteButton = new JButton("ɾ���ڵ�");
	JButton editButton = new JButton("�༭��ǰ�ڵ�");
	JTextArea jta = new JTextArea("sadasdasd");

	public void init(){
		//
		root = new DefaultMutableTreeNode("�й�");
		guangdong = new DefaultMutableTreeNode("�㶫");
		guangxi = new DefaultMutableTreeNode("����");
		foshan = new DefaultMutableTreeNode("��ɽ");
		shantou = new DefaultMutableTreeNode("��ͷ");
		guilin= new DefaultMutableTreeNode("����");
		nanning = new DefaultMutableTreeNode("����");
		guangdong.add(foshan);
		guangdong.add(shantou);
		guangxi.add(guilin);
		guangxi.add(nanning);
		root.add(guangxi);
		root.add(guangdong);
		tree = new JTree(root);
		tree.setEditable(true);
		model = (DefaultTreeModel)tree.getModel();
		
		jf = new JFrame("�ɱ༭�ڵ����");
		JPanel panel = new JPanel();
		panel.add(addSiblingButton,BorderLayout.SOUTH);
		panel.add(addChildButton,BorderLayout.SOUTH);
		panel.add(deleteButton,BorderLayout.SOUTH);
		panel.add(editButton,BorderLayout.SOUTH);
		panel.add(new JScrollPane(tree),BorderLayout.CENTER);
		Box box = new Box(BoxLayout.X_AXIS);
		box.add(panel);
		box.add(jta);
		
		jf.add(box);
		jf.pack();
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addSiblingButton.addActionListener(event -> {
			DefaultMutableTreeNode selectedNode 
				= (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
			if(selectedNode == null) return ;
			DefaultMutableTreeNode parent = (DefaultMutableTreeNode)
					selectedNode.getParent();
			if(parent == null) return ;
			DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("�ֵܽڵ�");
			int selectedIndex = parent.getIndex(selectedNode);
			model.insertNodeInto(newNode, parent, selectedIndex + 1);
		});
		
		editButton.addActionListener(event -> {
			TreePath selectedPath = tree.getSelectionPath();
			if(selectedPath != null){
				tree.startEditingAtPath(selectedPath);
			}
		});
		
		tree.addTreeSelectionListener(e -> {
			jta.append("��ѡ�нڵ�·��:"
					+e.getNewLeadSelectionPath().toString()+"\n");
		});
	}
	public static void main(String[] args) {
		new EditJTree().init();
	}
}
