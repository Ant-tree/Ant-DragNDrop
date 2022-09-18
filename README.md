# Ant-DragNDrop
Drag-N-Drop-able list with recycler view

<table>

<tr>
<td>
Swipe change (reorder)
</td>
<td>
Deletable items  
</td>
<td>
For bigger items  
</td>
</tr>

<tr>
</tr>

<tr>
<td>
<img src="Assets/swipe.gif" alt="demo">
</td>
<td>
<img src="Assets/deletion.gif" alt="selectable">
</td>
<td>
<img src="Assets/longlist.gif" alt="selectable">
</td>
</tr>

</table>



## Usage

Assemble the card list to represent the images you have

You can modify the items in ```model/Card.java```

```java
ArrayList<Card> cardList = new ArrayList<>();
cardList.add(new Card(0, "Card1", getResources().getDrawable(R.drawable.card1)));
cardList.add(new Card(0, "Card2", getResources().getDrawable(R.drawable.card2)));
cardList.add(new Card(0, "Card3", getResources().getDrawable(R.drawable.card3)));
cardList.add(new Card(0, "Card4", getResources().getDrawable(R.drawable.card4)));
```

Define an recyclerView with ```android:orientation="horizontal"``` attribute

```xml
<androidx.recyclerview.widget.RecyclerView
	android:layout_gravity="center"
	android:id="@+id/recyclerView"
	android:layout_width="wrap_content"
	android:layout_height="wrap_content"
	android:fadingEdge="none"
	android:orientation="horizontal"
	android:scrollbars="none" />
```

Attach ```DragNDropRecyclerView``` with predefined data list and recyclerView

```java
RecyclerView recyclerView = findViewById(R.id.recyclerView);
new DragNDropRecyclerView(CardArrangeActivity.this) {
	@Override
	public void onDataChanged(ArrayList<Card> arrayList) {
		
	}

	@Override
	public void onDragEnded(float v, float v1) {
		
	}

	@Override
	public void onDeleted(Card card) {
		
	}

	@Override
	public void onCardEnter(boolean b) {
		
	}

	@Override
	public void onCardSelected(String s) {
		
	}
}.initView(recyclerView, cardList);
```

