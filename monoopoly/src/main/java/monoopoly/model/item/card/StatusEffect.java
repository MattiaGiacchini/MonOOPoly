package monoopoly.model.item.card;

import java.util.Objects;

public class StatusEffect extends AbstractCardDecorator {

	private boolean goToJail;
	private boolean goToJailSetted;
	private boolean exitFromJail;
	private boolean exitFromJailSetted;
	private boolean maintainable;
	private boolean maintainableSetted;
	
	public static class Builder {
		private Card 	cardToDecore;
		private boolean goToJail;
		private boolean goToJailAdded;
		private boolean exitFromJail;
		private boolean exitFromJailAdded;
		private boolean maintainable;
		private boolean maintainableAdded;
		
		public Builder() {
			this.exitFromJail 		= false;
			this.exitFromJailAdded 	= false;
			this.goToJail 			= false;
			this.goToJailAdded		= false;
			this.maintainable 		= false;
			this.maintainableAdded	= false;
			this.cardToDecore 		= null;
		}

		public Builder cardToDecore(Card card) {
			Objects.requireNonNull(card,"The decorator cannot decor a null pointer");
			this.cardToDecore = card;
			return this;
		}
		
		public Builder exitFromJail(boolean value) {
			this.exitFromJail = value;
			this.exitFromJailAdded = true;
			return this;
		}
		
		public Builder goToJail(boolean value) {
			this.goToJail = value;
			this.goToJailAdded = true;
			return this;
		}
		
		public Builder maintainable(boolean value) {
			this.maintainable = value;
			this.maintainableAdded = true;
			return this;
		}
		
		public StatusEffect build() {
			if((!this.maintainableAdded &&
			   !this.exitFromJailAdded &&
			   !this.goToJailAdded) || 
			   (this.maintainable &&
			   !this.exitFromJail &&
			   !this.goToJail) ||
			   (this.exitFromJail &&
			    this.goToJail) || 
			   (this.goToJail &&
			    this.maintainable)) {
				throw new IllegalStateException();
			}
			return new StatusEffect(this);
		}
		
	}
	public StatusEffect(Builder builder) {
		super(builder.cardToDecore);
		
		if(builder.exitFromJailAdded) {
			this.exitFromJailSetted = true;
			this.exitFromJail = builder.exitFromJail;
		}
		
		if(builder.goToJailAdded) {
			this.goToJailSetted = true;
			this.goToJail = builder.goToJail;
		}
		
		if(builder.maintainableAdded) {
			this.maintainableSetted = true;
			this.maintainable = builder.maintainable;
		}
	}
	
	@Override
	public boolean mustThePlayerGoToJail() {
		if(this.goToJailSetted) {
			return this.goToJail;
		} else {
			return super.mustThePlayerGoToJail();
		}
	}

	@Override
	public boolean canThePlayerExitFromJail() {
		if(this.exitFromJailSetted) {
			return this.exitFromJail;
		} else {
			return super.canThePlayerExitFromJail();
		}
	}

	@Override
	public boolean isThisCardMaintainable() {
		if(this.maintainableSetted) {
			return this.maintainable;
		} else {
			return super.isThisCardMaintainable();
		}
	}
}
