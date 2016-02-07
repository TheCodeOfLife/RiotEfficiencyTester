
public class DPSCalculator {

	CSpell spell;
	int atDamage;
	int abPower;
	double cooldown;
	double cdr;
	double total = 0;
	double tAPrat = 0;
	double tADrat = 0;
	int tBase = 0;


	public DPSCalculator(CSpell spell, int ap, int ad, int cdr){

		this.spell=spell;
		abPower = ap;
		atDamage= ad;
		this.cdr=cdr;

	}

	public double calculate(){
		setTotalRatios();
		setTotalBase();
		setCooldown();

		if(spell.isToggle()){
			//System.out.println("This spell is a toggle! DPS Not Calculated due to toggles' low cooldowns.");
			tADrat=0;
		}

		if(spell.spell.getName().equals("Decimating Smash")) tADrat = 0.6;
		if(spell.spell.getName().equals("H-28G Evolution Turret")){
			tAPrat = 0.55;
			tBase = 130;
		}
		if(spell.spell.getName().equals("Astral Infusion")){
			tAPrat = .6;
			tBase = 240;
		}
		/*	
		System.out.println("total ap ratio: "+ tAPrat);
		System.out.println("total ap: "+ abPower);
		System.out.println("total ad ratio: "+ tADrat);
		System.out.println("total ad: "+ atDamage);
		System.out.println("total base damage: "+ tBase);
		System.out.println("Cooldown: "+ cooldown);
		System.out.println("total cdr: "+ cdr);
	//*/
		int dps = (int)((tAPrat*abPower+tADrat*atDamage+tBase)/(cooldown*((100-cdr)/100)));
		if(cooldown==0){
			dps = 0;
		}
		reset();
		return dps;
	}

	public void setCooldown(){
		cooldown = spell.getCooldown();
	}

	public void setTotalBase(){
		for(int i=0;i<spell.getDmgEffects().size();i++){
			tBase = tBase + Integer.parseInt(spell.getDmgEffects().get(i).getBase());
		}
	}

	public void setTotalRatios(){
		if(spell.isToggle()) return;
		String link;
		for(int i=0;i<spell.getLinks().length;i++){
			link = spell.getLinks()[i];
			if(link.equals("spelldamage")||link.equals("@dynamic.abilitypower")){
				tAPrat = tAPrat + spell.getSpellRatios()[i];
			}
			else if(link.equals("bonusattackdamage")||link.equals("attackdamage")||link.equals("@dynamic.attackdamage")){
				tADrat = tADrat + spell.getSpellRatios()[i];
			}
			else{
				//System.out.println(spell.spell.getName() +": This Link has no ratios");
			}
		}
	}

	public void reset(){
		total = 0;
		tAPrat = 0;
		tADrat = 0;
		tBase = 0;
	}

}
