PopUpMenuEdit {
	var <view,editView,menuView,saveView,deleteView,<font,<>selectionAction;

	*new { arg parent,bounds;
		bounds = bounds ? (parent.notNil.if { parent.view.bounds } { nil } ? Rect(100,300,120,20));
		^super.new.init(parent,bounds)
	}

	init { arg parent,bounds;
		view = View(parent,bounds).onResize_({ |me|
			editView.maxWidth_(menuView.bounds.width - 17)
		}).maxHeight_(20);

		editView = TextField().minWidth_(84).action_({ arg field;
			saveView.doAction
		});
		menuView = PopUpMenu().minWidth_(100).action_({ arg menu;
			/*(menu.value == 0).if {
				deleteView.visible_(false)
			} {
				deleteView.visible_(true)
			}*/
			editView.string_(menu.item);
			selectionAction.isKindOf(Function).if { selectionAction.(menu) }
		});
		saveView = Button().states_([
			["Save"/*,Color.black,Color(255/255,215/255,127/255)*/]
		]).maxWidth_(60).maxHeight_(20).action_({ arg butt;
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
		deleteView = Button().states_([["Delete"]]).maxWidth_(60).maxHeight_(20).action_({ arg butt;

		}).visible_(false);

		view.layout_(
			GridLayout.rows([
				StackLayout(editView,menuView).mode_(1),
				GridLayout.rows([saveView,deleteView]).margins_(0).hSpacing_(0)
			]).hSpacing_(4).vSpacing_(0).margins_(0).setColumnStretch(0,4).setColumnStretch(1,1)
		)
	}

	font_ { arg newFont;
		editView.font_(newFont);
		saveView.font_(newFont);
		deleteView.font_(newFont)
	}

	items_{ arg stringArray;
		menuView.items_([""] ++ stringArray)
	}

	items {
		^menuView.items
	}

	item {
		^menuView.item
	}

	allowsReselection {
		^menuView.allowsReselection
	}

	allowsReselection_ { arg flag;
		menuView.allowsReselection_(flag)
	}

}