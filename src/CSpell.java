import java.util.Arrays;
import java.util.List;

import com.robrua.orianna.type.core.staticdata.ChampionSpell;
import com.robrua.orianna.type.core.staticdata.SpellVars;

public class CSpell {

	ChampionSpell spell;

	public CSpell(ChampionSpell spell){
		this.spell=spell;
	}

	public List<SpellVars> getSpellVars(){
		return spell.getSpellVars();
	}

	public String[] getLinks(){
		String [] linkArray = new String[getNumVars()];

		for(int i=0;i<linkArray.length; i++){
			linkArray[i] = spell.getSpellVars().get(i).getLink();
		}
		return linkArray;
	}

	public Double[] getSpellRatios(){
		Double[] ratioArray = new Double[getNumVars()];

		for(int i=0;i<ratioArray.length; i++){
			ratioArray[i] = getMaxCoeff(spell.getSpellVars().get(i).getCoeffs());
		}

		return ratioArray;
	}

	public List<String> getEffectBurn(){
		return spell.getEffectBurn();
	}
	
	public List<List<Double>> getEffect(){
		return spell.getEffect();
	}

	public List<String> getLabel(){
		return spell.getLevelTip().getLabel();
	}

	public int parseEffectIndex(String lvltipeffect){
	/*	
		if(spell.getName().equals("Buckshot")||
		   spell.getName().equals("Hiten Style")||
		   spell.getName().equals("Pick A Card")||
		   spell.getName().equals("Stacked Deck")||
		   spell.getName().equals("Empower")) {
			return Integer.parseInt(lvltipeffect.substring(5,6));

		}
		*/
		int i = 0;
		if(spell.getName().equals("Tidal Wave")) while (!Character.isDigit(lvltipeffect.charAt(i))) i++;
		while (!Character.isDigit(lvltipeffect.charAt(i))) i++;
		
		return Integer.parseInt(lvltipeffect.substring(i,i+1));
	}

	public boolean[] ifDamaging(){
		boolean[] boolArray = new boolean[getLabel().size()];
		List<String> damageList;
		damageList = Arrays.asList("Damage","Primary Damage","Cone Damage", "Max Damage", "Total Damage", 
				"Passive Damage", "Active Damage", "True Damage", "Sonic Wave Damage",
				"Resonating Strike Base Damage", "Tempest Damage", "Neurotoxin Damage",
				"Venomous Bite Damage", "Spider Form Bite Damage", "Bonus Damage", "Collision Damage",
				"Bite Damage", "Blue Card Damage", "Flight Damage", "Base Damage",
				"Damage per Bounce", "Magic Damage", "On-hit Damage", "Spin Damage",
				"Flat Damage", "Queen's Wrath Damage", "Prey Seeker Damage", "Un-burrow Damage",
				"Skystrike Damage", "Initial Damage", "Secondary Damage", "Base Charge Damage",
				"Bonus Attack Damage", "Maximum Damage", "Bounce Damage", "Percent of Attack",
				"Blast Damage / Attack Damage", "Impact Damage", "Damage ", "Damage Per Second",
				"Fury Damage", "Critical Strike Damage", "Shiv Damage", "On Death Damage", 
				"Base Damage Over Time", "Mine Damage", "Bleed Damage", "Damage Per Second Charged",
				"Outgoing Damage", "Return Damage", "Health Drained", "Enemy Champion Damage",
				"Mimicked Sigil of Malice Damage", "Mimicked Distortion Damage", "Mimicked Ethereal Chains Damage",
				"Spectral Attack Damage", "Damage to 1st target", "Damage to 2nd target", "Lightning Damage",
				"Activation Damage", "Damage Dealt", "Attack Damage Bonus", "Damage Per Wave ", "Mark Damage");
		for(int i=0;i<getLabel().size();i++){
			boolArray[i]=damageList.contains(getLabel().get(i))&&!isToggle();
		}
		return boolArray;
	}
	
	public boolean isToggle(){
		return spell.getSanitizedTooltip().contains("toggle")||
			   spell.getSanitizedTooltip().contains("Fishbones")||
			   spell.getSanitizedTooltip().contains("toggled")||
			   spell.getSanitizedTooltip().contains("Toggle:");
				
	}

	public String getSpecificEffect(int index){
		//if(getEffectBurn().equals(null)){
			if(getEffect().isEmpty()) return Integer.toString(0);
			return Integer.toString(getEffect().get(index).get(getEffect().get(index).size()-1).intValue());
		//}
		//String uncropped = getEffectBurn().get(index);
		//String[] cropped = uncropped.split("/");
		//return cropped[cropped.length-1]; 					//return last or highest dmg
	}

	public List<String> getLvltipEffect(){
		return spell.getLevelTip().getEffect();
	}

	public double getCooldown(){
		if(spell.getName().equals("Force of Will")) return 8;
		else if(spell.getName().equals("Electro Harpoon")) return 10/2;
		else if(spell.getName().equals("Excessive Force")) return 8/2;
		else if(spell.getName().equals("Savagery")) return 4;
		else if(spell.getName().equals("Shadow Dance")) return 15/3;
		else if(spell.getName().equals("Battle Roar")) return 12;
		else if(spell.getName().equals("Bola Strike")) return 8;
		else if(spell.getName().equals("Noxious Trap")) return 22/3;
		else if(spell.getName().equals("Void Rift")) return 15/2;
		else if(spell.getName().equals("Eye of Destruction")) return 8;
		else if(spell.getName().equals("H-28G Evolution Turret")) return 20/3;

		
		return spell.getCooldown().get(spell.getCooldown().size()-1);
	}

	public double getMaxCoeff(List<Double> coeffs){
		return coeffs.get(coeffs.size()-1);
	}

	public int getNumVars(){
		return spell.getSpellVars().size();
	}
	
	public Integer getRange(){
		if(spell.getRange().equals(null)) return 0;
		return spell.getRange().get((spell.getRange().size()-1));
	}

	public List<DmgEffect> getDmgEffects(){
		boolean[] isDmg = ifDamaging();
		int size = 0;
		int count = 0;
		
		

		for(int i=0;i<isDmg.length;i++){
			if(isDmg[i]) size++;
		}

		DmgEffect[] dmgArray = new DmgEffect[size];

		for(int i=0;i<isDmg.length;i++){
			if(isDmg[i]){

					dmgArray[count] = new DmgEffect(this, i);
					count++;
				
			}

		}
		return Arrays.asList(dmgArray);
	}

	public class DmgEffect{

		CSpell spell;
		int index;

		public DmgEffect(CSpell spell, int index){
			this.spell=spell;
			this.index=index;
		}

		public String getlabel(){
			return spell.getLabel().get(index);
		}

		public String getLink(){
			return spell.getLinks()[index];
		}

		public double getCoeff(){
			List<Double> coeffs=spell.getSpellVars().get(index).getCoeffs();
			return spell.getMaxCoeff(coeffs);
		}
		


		public String getBase(){
			String effect = spell.getLvltipEffect().get(index);

			if(spell.spell.getName().equals("Tidal Wave")) effect = spell.getLvltipEffect().get(index+1);
			
			int effectIndex = spell.parseEffectIndex(effect);
			return spell.getSpecificEffect(effectIndex);

		}

	}

}
