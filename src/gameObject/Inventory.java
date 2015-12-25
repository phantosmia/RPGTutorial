package gameObject;

import item.Item;

public class Inventory {
	private Item[] itens;
	private int firstFree;
	public Inventory(int size) {
		itens = new Item[size];
		firstFree = 0;
	}
	public boolean add(Item item) {
		if (firstFree == itens.length)
			return false;
		itens[firstFree] = item;
		for (int i = firstFree + 1; i < itens.length; i++)
			if (itens[i] == null) {
				firstFree = i;
				return true;
			}
		firstFree = itens.length;
		return false;
	}

	public Item get(int index) {
		return itens[index];
	}

	public void remove(int index) {
		itens[index] = null;
		if (index < firstFree)
			firstFree = index;
	}

	public void remove(Item item) {
		for (int i = 0; i < itens.length; i++) {
			if (itens[i] == item) {
				remove(i);
				return;
			}
		}
	}
}
