package Objects;


import java.awt.image.BufferedImage;

public class Champion {
	private BufferedImage ChampionIcon;
	private String ChampionName;
	private int ChampionCode;
	
	public Champion(BufferedImage icon, String name, int code) {
		this.setChampionIcon(icon);
		this.setChampionName(name);
		this.setChampionCode(code);
	}

	public BufferedImage getChampionIcon() {
		return ChampionIcon;
	}

	public void setChampionIcon(BufferedImage championIcon) {
		ChampionIcon = championIcon;
	}

	public String getChampionName() {
		return ChampionName;
	}

	public void setChampionName(String championName) {
		ChampionName = championName;
	}

	public int getChampionCode() {
		return ChampionCode;
	}

	public void setChampionCode(int championCode) {
		ChampionCode = championCode;
	}
}
