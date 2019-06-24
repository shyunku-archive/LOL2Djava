package Global;

import java.util.ArrayList;

import Objects.Champion;

public class ChampionManager {
	public ArrayList<Champion> pack = new ArrayList<>();
	
	public ChampionManager() {
		pack.add(new Champion(ImageManager.AmumuIconImage, "아무무", 10001));
		pack.add(new Champion(ImageManager.DariusIconImage, "다리우스", 10002));
		pack.add(new Champion(ImageManager.JaxIconImage, "잭스", 10003));
		pack.add(new Champion(ImageManager.JinxIconImage, "징크스", 10004));
		pack.add(new Champion(ImageManager.TrindamereIconImage, "트린다미어", 10005));
		pack.add(new Champion(ImageManager.YasuoIconImage, "야스오", 10006));
		pack.add(new Champion(ImageManager.SorakaIconImage, "소라카", 10007));
	}
	
	public Champion getChampionByCode(int code) {
		for(int i=0;i<pack.size();i++)
			if(pack.get(i).getChampionCode() == code)
				return pack.get(i);
		return null;
	}
}
