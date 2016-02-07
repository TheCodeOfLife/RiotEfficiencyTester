import java.util.List;
import java.util.Scanner;

import com.robrua.orianna.api.core.RiotAPI;
import com.robrua.orianna.type.core.common.Region;
import com.robrua.orianna.type.core.staticdata.Champion;

public class EfficiencyFinder {

	public static void main(String[] args){


		int champAD;
		int champAP;
		int champCDR;
		double maxForChamp = 0;
		double maxForAll = 0;
		String maxSpellName = null;
		String cMaxSpellName = null;
		String maxChampName = null;
		String cMaxChampName = null;
		int maxDamage = 0;

		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);

		DPSCalculator calc;
		Champion curC;
		CSpell curS;

		RiotAPI.setMirror(Region.NA);
		RiotAPI.setRegion(Region.NA);
		RiotAPI.setAPIKey("3f03f8a6-2d27-446e-9403-541d47212b38");

		//Get Input


		System.out.println("Enter Champion AP, AD, and CDR, in that order.");
		champAP = scan.nextInt();
		champAD = scan.nextInt();
		champCDR = scan.nextInt();



		List<Champion> champList = RiotAPI.getChampions();

		for(int i=0;i<champList.size();i++){ 					//go through each champion
			curC = champList.get(i);
			maxForChamp = 0;
			for(int j=0;j<curC.getSpells().size();j++){			//go through each spell of champion
				curS= new CSpell(curC.getSpells().get(j));
				calc = new DPSCalculator(curS, champAP, champAD, champCDR);
				calc.calculate();	
				if(calc.calculate()>maxForChamp){				//if spell dps bigger than max so far, switch
					maxForChamp = calc.calculate();
					cMaxSpellName = curS.spell.getName();
					cMaxChampName = curC.getName();
				}
				if(maxForChamp>maxForAll){ maxForAll = maxForChamp;
				maxSpellName = cMaxSpellName;
				maxChampName = cMaxChampName;
				maxDamage = (int) maxForChamp;

				}

			}
		}
		
		System.out.println("The most efficient spell based on "+champAP+" AP, "+champAD+" AD, and "+champCDR+"% CDR is "+maxChampName+"'s "+maxSpellName+" with "+maxDamage+" Damage Per Second.");

	}
}

