PopUpMenuEdit {
	var <view,editView,menuView,saveView,<font;

	*new { arg parent,bounds;
		bounds = bounds ? (parent.notNil.if { parent.view.bounds } { nil } ? Rect(100,300,120,20));
		^super.new.init(parent,bounds)
	}

	init { arg parent,bounds;
		view = View(parent,bounds).onResize_({ |me|
			editView.maxWidth_(me.bounds.width - 16 - 68)
		}).maxHeight_(20).resize_(5);

		editView = TextField().minWidth_(84);
		menuView = PopUpMenu().minWidth_(100);
		saveView = Button().states_([
			["Save"/*,Color.black,Color(255/255,215/255,127/255)*/]
		]).maxWidth_(64).maxHeight_(20).action_({ arg butt;
			// only save/re-save when a string > 0 has been entered in the textfield
			(editView.string.size > 0).if {
				menuView.items.isNil.if {
					menuView.items_(["",editView.string]);
					menuView.value_(1)
				} {
					(menuView.value == 0).if {
						// if its the first, prepend item to items array
						menuView.items_(menuView.items.insert(1,editView.string))
					} {
						// if its already a valid item, replace item instead
						menuView.items_(menuView.items.put(menuView.value,editView.string))
					}
				}
			}
		});

		view.layout_(
			GridLayout.rows(
				[StackLayout(editView,menuView).mode_(1),saveView]
			).hSpacing_(4).vSpacing_(0).margins_(0).setColumnStretch(0,4).setColumnStretch(1,1)
		)
	}

	font_ { arg newFont;
		editView.font_(newFont);
		saveView.font_(newFont)
	}

	items_{ arg stringArray;
		menuView.items_(stringArray)
	}

	items {
		^menuView.items
	}

	item {
		^menuView.item
	}

}