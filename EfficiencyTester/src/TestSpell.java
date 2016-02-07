import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.robrua.orianna.api.core.RiotAPI;
import com.robrua.orianna.type.core.common.Region;
import com.robrua.orianna.type.core.staticdata.Champion;

public class TestSpell {

	public static void main(String[] args){
		int champID;
		int spellKey;
		int champAD;
		int champAP;
		int champCDR;
		double maxForChamp = 0;
		double maxForAll = 0;
		List<Double> maxForChamps = new ArrayList<Double>();


		Scanner scan = new Scanner(System.in);

		DPSCalculator calc;
		Champion curC;
		CSpell curS;



		RiotAPI.setMirror(Region.NA);
		RiotAPI.setRegion(Region.NA);
		RiotAPI.setAPIKey("3f03f8a6-2d27-446e-9403-541d47212b38");

		//Get Input

		System.out.println("Enter champ ID");
		champID = scan.nextInt();
		System.out.println("Enter Spell key (q=1, w=2, e=3, r=4)");
		spellKey = scan.nextInt()-1;
		System.out.println("Enter Champion AP, AD, and CDR, in that order.");
		champAP = scan.nextInt();
		champAD = scan.nextInt();
		champCDR = scan.nextInt();

		
		CSpell testSpell = new CSpell(RiotAPI.getChampionByID(champID).getSpells().get(spellKey));
		
		calc = new DPSCalculator(testSpell, champAP, champAD, champCDR);
		System.out.println(testSpell.spell.getName());
		System.out.print(calc.calculate());
		
	}
}
