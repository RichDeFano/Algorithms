

public class redblacktree {

	  private treeNode Root;
	  
	  public treeNode getRoot() { return Root; }
	  
	  // Constructs a new empty red-black tree.
	  public redblacktree() { Root = Root.nullnode;}
	  public void clear() { Root = Root.nullnode; }
	  public boolean isEmpty() { return Root.isEmpty(); }	  

	  public int size() { return Root.size(); }
	  public static treeNode nullnode = new treeNode(0, -1);
	  public treeNode find(int key) {
		  if (Root.isEmpty()) return Root;
		  return Root.find(key);
	  }

	  public void checkRedBlack() {
		  if (Root.isRed) 
			  System.out.println( "The root is red");
		  Root.bheight();
	  }
          
          public boolean isBlack(treeNode t){
              if (t.isRed) {return false;}
              else
              {return true;}
          
          }
	  
	  public void insert(int id) { // call recursive insert
	       treeNode nnd = new treeNode(id);    // make new node

	       if (Root.isEmpty()) Root = nnd;
	       else if (id < Root.iData) {
	    	   if (Root.left.isEmpty())
	    		   Root.left = nnd;
	    	   else if (insert(nnd, Root.left, Root)) {
	  	    	   // red-red violation exists at Root.left and its children
			       if (Root.right.isRed) { // red uncle case:
			    	   Root.right.isRed = Root.left.isRed = false;
			       } else { // black uncle case:			    	   
			    	   Root.isRed = true;
			           if (nnd.iData < Root.left.iData) {
			               Root = Root.rotateToRight();
			           } else {
			               Root = Root.doubleRotateToRight();
			           } 			 
			       } // else
	    	   }
	       } else if (Root.right.isEmpty()) {
	    		   Root.right = nnd;
	       } else if (insert(nnd, Root.right, Root)) {
  	    	   // red-red violation exists at Root.right and its children
		       if (Root.left.isRed) { // red uncle case:
		    	   Root.right.isRed = Root.left.isRed = false;
		       } else { // black uncle case:		    	   
		    	   Root.isRed = true;
		           if (nnd.iData >= Root.right.iData) {
		               Root = Root.rotateToLeft();
		           } else {
		               Root = Root.doubleRotateToLeft();
		           } 
		       } // else
	       }
	       Root.isRed = false;
	    }  // end insert()
          
          public void remove(int id) {
            Root = remove(id, Root);
        }
        
          public boolean isLeaf(treeNode t){
            if ((t.left.isEmpty()) && (t.right.isEmpty()))
            {return true;}
            else
            {return false;}
        
        }
          
    
	  
	  /* *************************************************** *
	   *  PRIVATE METHODS                                    *
	   * *************************************************** */
	  
	  /* Inserts a node into tree and returns a boolean */
	  private boolean insert(treeNode nnd, treeNode t, treeNode par) {
		  // return true iff t is red and t has a red child

		  if (nnd.iData < t.iData) {
		      if (t.left.isEmpty()) {
		         t.left = nnd;  //attach new node as leaf
		      } else if (insert(nnd, t.left, t)) { 
		    	 // red-red violation exists at t.left and its children
		    	 if (t.right.isRed) { // red uncle case:
		    		 t.right.isRed = t.left.isRed = false;
		    		 t.isRed = true;
		    	 } else { // black uncle case:
		    		 treeNode nt;
		    		 
		             if (nnd.iData < t.left.iData) {
		                nt = t.rotateToRight();
		             } else {
		                nt = t.doubleRotateToRight();
		             } 
		    		 t.isRed = true;
		    		 nt.isRed = false;
		    		 if (nt.iData < par.iData) par.left = nt;
		    		 else par.right = nt;
		         } //if
		      }
		      
		      return (t.isRed && t.left.isRed);
		 } else { // branch right
		      if (t.right.isEmpty()) {
		           t.right = nnd;   // attach new node as leaf
			  } else if (insert(nnd, t.right, t)) { 
			       // red-red violation exists at t.right and its children
			       if (t.left.isRed) { // red uncle case:
			    		 t.right.isRed = t.left.isRed = false;
			    		 t.isRed = true;
			       } else { // black uncle case:
			    		 treeNode nt;
			    		 
			             if (nnd.iData >= t.right.iData) {
			                nt = t.rotateToLeft();
			             } else {
			                nt = t.doubleRotateToLeft();
			             } 
			    		 t.isRed = true;
			    		 nt.isRed = false;
			    		 if (nt.iData < par.iData) par.left = nt;
			    		 else par.right = nt;
			       } // else
			  } 
		      
			  return (t.isRed && t.right.isRed);
		 } // else
	  }  // end insert
          
private treeNode remove(int x, treeNode t){
 if (t.isEmpty()) return t; //If the tree is found empty         
    if (t.iData == x)
    {
        //For deletion of a node with two non leaf children.
        if (!(isLeaf(t.left)) && !(isLeaf(t.right)))
            {
                treeNode pred = t.predecessor(x);
                treeNode succ = t.successor(x);
                if (pred == nullnode)
                    {
                        t.iData = succ.iData;
                        succ = nullnode;
                    }
                else
                    {
                        t.iData = pred.iData;
                        pred = nullnode;
                    }
            }
        else
            {
            treeNode childNode;
                if (isLeaf(t.left) == true)
                {childNode = t.left;}
                else
                {childNode = t.right;}
                    //For deletion of a red node:
                    if (isBlack(t) == false)
                        {
                            t.iData = childNode.iData;
                            childNode = nullnode;
                        }
                    //For deletion of a black node/fixing of double black nodes.
                    else
                        {
                        treeNode siblingNode = findSibling(t);
                        treeNode parentNode = findParent(t);
                            
                            if (siblingNode.isRed == false) //Case 1: Black sibling 
                            {   
                                //Case 1B: Black Sibling, Red Child.
                                if ((siblingNode.left.isRed == true) || (siblingNode.right.isRed == true))
                                {
                                    t = t.rotateToLeft();
                                    siblingNode.left.isRed = false;
                                    siblingNode.right.isRed = false;
                                }
                                //Case 1C: Black Sibling, Black Children.
                                if ((siblingNode.left.isRed == false) && (siblingNode.right.isRed == false))
                                {
                                    if (parentNode.isRed == true)
                                    {
                                        parentNode.isRed = false;
                                        siblingNode.isRed = true;
                                    }
                                    else       //Case 1D: parent becomes double black. must continue upward
                                    {
                                        parentNode.isRed = false;
                                        siblingNode.isRed = false;
                                    }
                                }
                            }    
                            //Case 2C: The sibling of the DBlack node is red
                                    //Need to adjust the tree
                            else
                            {
                                t = t.rotateToLeft();
                                siblingNode.isRed = false;
                                parentNode.isRed = true;
                            }
                                       
                                    
                    
                        }

            }
    }

    
    else if (t.iData < x)
        {t.left = remove(x,t.left);}
    else
        {t.right = remove(x,t.right);}
    return t;
}

private treeNode findParent(treeNode t)
{
    treeNode parentNode = this.getRoot();
    int x = t.iData;
    
        if (x < t.left.iData) {
            t.left = findParent(t.left);
        } 
        else if( x > t.right.iData) {
            t.right = findParent(t.right);
        }
        else if ( x == t.left.iData)
        {
            parentNode = t.left;
            return parentNode;
        }
        
         else if ( x == t.right.iData)
        {
            parentNode = t.right;
            return parentNode;
        }

    
    
    
    
    
    return parentNode;
}

private treeNode findSibling(treeNode t)
{
    treeNode parentNode = findParent(t);
    if (parentNode.left.iData == t.iData)
    {
        return parentNode.right;
    }
    else
    {
        return parentNode.left;
    }
}





}