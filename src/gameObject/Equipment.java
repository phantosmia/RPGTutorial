package gameObject;

import item.EquippableItem;
import item.Item;

public class Equipment {
	private EquippableItem[] itens;
	private Inventory inv;
	public Equipment(Inventory inv){
		itens = new EquippableItem[EquippableItem.NUM_SLOTS];
		this.inv = inv;
	}
	public boolean equip(EquippableItem item){
		int index = item.getSlot();
		if(itens[index] != null){
			if(!deEquip(index))
				return false;
		}
		inv.remove(item);
		itens[index] = item;
		return true;
	}
	public boolean deEquip(int slot){
		if(inv.add(itens[slot])){
		itens[slot] = null;
		return true;
		}
		return false;
	}
}
