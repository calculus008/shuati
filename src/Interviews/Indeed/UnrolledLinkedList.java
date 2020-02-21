package Interviews.Indeed;

public class UnrolledLinkedList {
	UnRolledList list;
	public UnrolledLinkedList(){
		list = new UnRolledList(new Node(),100);
	}
	public char get(int index){ 
		Node node = list.head;
		int  total = list.totalLen;
		if(node == null || total < 0 || index > total){
			return ' ';
		}
		while(node!=null && node.len < index){
			index -= node.len;
			node = node.next;
		}
		if(node == null){
			return  ' ';
		}
		return node.chars[index];
	}
	public void insert(char ch, int index){
		Node node = list.head;
		int  total = list.totalLen;
		if(node == null || total < 0 || index > total){
			return ;
		}
		while(node!=null && node.len < index){
			index -= node.len;
			node = node.next;
		}
		if(node == null){
			return;
		}
		if(node.len < 5) {
			for(int  i = node.len; i>index; i--){
				node.chars[i] = node.chars[i-1];
			}
			node.chars[index] = ch;
		} else  {
			Node newNode = new Node();
			newNode.len = 5-index;
			for(int  i = index; i<node.len; i++){
				newNode.chars[i-index] = node.chars[i];
				 node.chars[i] =' ';
			}
			node.chars[index] = ch;
			node.len= index;
			newNode.next = node.next;
			node.next = newNode;
		}
	}
	public static void main(String[] args) {

	}
	class Node {
        char[] chars = new char[5];  
        int len;
        Node next;
        public  Node (){
        	
        }
	}
	class UnRolledList {
	        Node head;
	        int totalLen;
	        public UnRolledList(Node head, int  total){
	        	this.head= head;
	        	this.totalLen= total;
	        }
	}
}
