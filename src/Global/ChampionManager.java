package Global;

import java.util.ArrayList;

import Objects.Champion;

public class ChampionManager {
	public ArrayList<Champion> pack = new ArrayList<>();
	
	public ChampionManager() {
		pack.add(new Champion(ImageManager.AmumuIconImage, "�ƹ���", 10001));
		pack.add(new Champion(ImageManager.DariusIconImage, "�ٸ��콺", 10002));
		pack.add(new Champion(ImageManager.JaxIconImage, "�轺", 10003));
		pack.add(new Champion(ImageManager.JinxIconImage, "¡ũ��", 10004));
		pack.add(new Champion(ImageManager.TrindamereIconImage, "Ʈ���ٹ̾�", 10005));
		pack.add(new Champion(ImageManager.YasuoIconImage, "�߽���", 10006));
	}
}
