package intrepreteur;

import java.util.ArrayList;


public class Pile<T> {
    
    private ArrayList<T> elements = new ArrayList<>();
	

    public Pile() {
    }
    
    public T getElement(){
        return elements.get(elements.size()-1);
    }
    
    public T depiler(){
        int index = elements.size()-1;
        T t = elements.get(index);
        elements.remove(index);
        return t;
    }
    
    public void empiler(T element){
        elements.add(element);
    }
    public T sommet(){
    	return elements.get(elements.size()-1);
    			}

	@Override
	public String toString() {
		return "Pile [elements=" + elements + "]";
	}
}

