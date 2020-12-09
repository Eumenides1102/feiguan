package algorithm;

import java.util.ArrayList;

//version 1: traverse
public class Selection1 {
	public ArrayList<Integer> preorderTraversal(TreeNode root)
	{
		ArrayList<Integer> result = new ArrayList<Integer>();
		traverse(root, result);
		return result;
	}
	
	//��rootΪ����preorder����result����
	private void traverse(TreeNode root, ArrayList<Integer> result)
	{
		if(root == null)
		{
			return;
		}
		
		result.add(root.val);
		traverse(root.left, result);
		traverse(root.right, result);
	}
}

