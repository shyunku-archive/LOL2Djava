package Game.Objects.Champions;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import Game.Utility.Gage;
import Objects.Coordinate;
import Objects.CoordinateDouble;

public abstract class ChampionEngine {
	/* ------------------ 챔피언 정보 ------------------ */
	private String ChampionName;
	private int ChampionCode;
	private BufferedImage ChampionIcon;
	private BufferedImage ChampionImage;
	
	private BufferedImage Q_SkillImage;
	private BufferedImage W_SkillImage;
	private BufferedImage E_SkillImage;
	private BufferedImage R_SkillImage;
	
	/* ------------------ 챔피언 스탯 ------------------ */
	private int LEVEL;						//레벨
	private Gage exp;						//경험치


	private int SP;							//스킬포인트
	
	private Gage HP;						//체력
	private Gage MP;						//마나 또는 소모 매개체
	private double AttPower;				//공격력
	private double MagicPower;				//주문력
	private double AttSpeed;				//공격속도
	private double AttRange;				//사정거리
	
	private double physicArmor;				//물리방어력
	private double magicArmor;				//마법방어력
	
	private double physicArmorPenetrate;	//물리 관통력
	private double magicArmorPenetrate;		//마법 관통력
	
	private double HPgenPerSec;				//1초당 체젠
	private double MPgenPerSec;				//1초당 마젠
	
	
	private double MoveSpeed;				//이동속도
	private CoordinateDouble pos;			//위치 좌표
	
	
	//성장치
	private double plusHP;
	private double plusMP;
	private double plusAttSpeed;
	private double plusAttPower;
	private double plusPhysicArmor;
	private double plusMagicArmor;
	private double plusHPgen;
	private double plusMPgen;
	
	public abstract void draw(Graphics2D g);
	
	public ChampionEngine(BufferedImage icon, BufferedImage image) {
		this.LEVEL = 0;
		this.exp = new Gage(180);
		this.SP = 0;
		this.MagicPower = 0;
		this.pos = new CoordinateDouble(0,0);
	}
	
	public void setInitialChampionStatus(double hp, double mp, double attpow, double attspeed, double attrange, 
			double pArm, double mArm, double pArmPen, double mArmPen, double HPgen, double MPgen, double moveSpeed) {
		this.HP = new Gage(hp);
		this.MP = new Gage(mp);
		this.AttPower = attpow;
		this.AttSpeed = attspeed;
		this.AttSpeed = attrange;
		this.physicArmor = pArm;
		this.magicArmor = mArm;
		this.physicArmorPenetrate = pArmPen;
		this.magicArmorPenetrate = mArmPen;
		this.HPgenPerSec = HPgen/5;
		this.MPgenPerSec = MPgen/5;
		this.MoveSpeed = moveSpeed;
	}
	
	public void setSkillIcon(BufferedImage qImage, BufferedImage wImage, BufferedImage eImage, BufferedImage rImage) {
		this.Q_SkillImage = qImage;
		this.W_SkillImage = wImage;
		this.E_SkillImage = eImage;
		this.R_SkillImage = rImage;
	}
	
	public void setPlusStatusPerLevelUp(double hp, double mp, double attspeed, double attpower, double pArm, double mArm, double HPgen, double MPgen) {
		this.plusHP = hp;
		this.plusMP = mp;
		this.plusAttPower = attpower;
		this.plusAttSpeed = attspeed;
		this.plusPhysicArmor = pArm;
		this.plusMagicArmor = mArm;
		this.plusHPgen = HPgen/5;
		this.plusMPgen = MPgen/5;
	}
	
	public void LevelUp() {
		this.LEVEL++;
		this.SP++;
		this.exp.setEmpty();
		this.exp.levelup(100);
		
		this.HP.levelup(this.plusHP);
		this.HP.addGage(this.plusHP);
		
		this.MP.levelup(this.plusMP);
		this.MP.addGage(this.plusMP);
		
		this.AttPower += this.plusAttPower;
		this.AttSpeed += this.plusAttSpeed;
		this.physicArmor += this.plusPhysicArmor;
		this.magicArmor += this.plusMagicArmor;
		
		this.HPgenPerSec += this.plusHPgen;
		this.MPgenPerSec += this.plusMPgen;
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

	public BufferedImage getChampionIcon() {
		return ChampionIcon;
	}

	public void setChampionIcon(BufferedImage championIcon) {
		ChampionIcon = championIcon;
	}

	public BufferedImage getChampionImage() {
		return ChampionImage;
	}

	public void setChampionImage(BufferedImage championImage) {
		ChampionImage = championImage;
	}

	public BufferedImage getQ_SkillImage() {
		return Q_SkillImage;
	}

	public void setQ_SkillImage(BufferedImage q_SkillImage) {
		Q_SkillImage = q_SkillImage;
	}

	public BufferedImage getW_SkillImage() {
		return W_SkillImage;
	}

	public void setW_SkillImage(BufferedImage w_SkillImage) {
		W_SkillImage = w_SkillImage;
	}

	public BufferedImage getE_SkillImage() {
		return E_SkillImage;
	}

	public void setE_SkillImage(BufferedImage e_SkillImage) {
		E_SkillImage = e_SkillImage;
	}

	public BufferedImage getR_SkillImage() {
		return R_SkillImage;
	}

	public void setR_SkillImage(BufferedImage r_SkillImage) {
		R_SkillImage = r_SkillImage;
	}

	public int getLEVEL() {
		return LEVEL;
	}

	public void setLEVEL(int lEVEL) {
		LEVEL = lEVEL;
	}

	public Gage getExp() {
		return exp;
	}

	public void setExp(Gage exp) {
		this.exp = exp;
	}

	public int getSP() {
		return SP;
	}

	public void setSP(int sP) {
		SP = sP;
	}

	public Gage getHP() {
		return HP;
	}

	public void setHP(Gage hP) {
		HP = hP;
	}

	public Gage getMP() {
		return MP;
	}

	public void setMP(Gage mP) {
		MP = mP;
	}

	public double getAttPower() {
		return AttPower;
	}

	public void setAttPower(double attPower) {
		AttPower = attPower;
	}

	public double getMagicPower() {
		return MagicPower;
	}

	public void setMagicPower(double magicPower) {
		MagicPower = magicPower;
	}

	public double getAttSpeed() {
		return AttSpeed;
	}

	public void setAttSpeed(double attSpeed) {
		AttSpeed = attSpeed;
	}

	public double getAttRange() {
		return AttRange;
	}

	public void setAttRange(double attRange) {
		AttRange = attRange;
	}

	public double getPhysicArmor() {
		return physicArmor;
	}

	public void setPhysicArmor(double physicArmor) {
		this.physicArmor = physicArmor;
	}

	public double getMagicArmor() {
		return magicArmor;
	}

	public void setMagicArmor(double magicArmor) {
		this.magicArmor = magicArmor;
	}

	public double getPhysicArmorPenetrate() {
		return physicArmorPenetrate;
	}

	public void setPhysicArmorPenetrate(double physicArmorPenetrate) {
		this.physicArmorPenetrate = physicArmorPenetrate;
	}

	public double getMagicArmorPenetrate() {
		return magicArmorPenetrate;
	}

	public void setMagicArmorPenetrate(double magicArmorPenetrate) {
		this.magicArmorPenetrate = magicArmorPenetrate;
	}

	public double getHPgenPerSec() {
		return HPgenPerSec;
	}

	public void setHPgenPerSec(double hPgenPerSec) {
		HPgenPerSec = hPgenPerSec;
	}

	public double getMPgenPerSec() {
		return MPgenPerSec;
	}

	public void setMPgenPerSec(double mPgenPerSec) {
		MPgenPerSec = mPgenPerSec;
	}

	public double getMoveSpeed() {
		return MoveSpeed;
	}

	public void setMoveSpeed(double moveSpeed) {
		MoveSpeed = moveSpeed;
	}

	public CoordinateDouble getPos() {
		return pos;
	}

	public void setPos(CoordinateDouble pos) {
		this.pos = pos;
	}

	public double getPlusHP() {
		return plusHP;
	}

	public void setPlusHP(double plusHP) {
		this.plusHP = plusHP;
	}

	public double getPlusMP() {
		return plusMP;
	}

	public void setPlusMP(double plusMP) {
		this.plusMP = plusMP;
	}

	public double getPlusAttSpeed() {
		return plusAttSpeed;
	}

	public void setPlusAttSpeed(double plusAttSpeed) {
		this.plusAttSpeed = plusAttSpeed;
	}

	public double getPlusAttPower() {
		return plusAttPower;
	}

	public void setPlusAttPower(double plusAttPower) {
		this.plusAttPower = plusAttPower;
	}

	public double getPlusPhysicArmor() {
		return plusPhysicArmor;
	}

	public void setPlusPhysicArmor(double plusPhysicArmor) {
		this.plusPhysicArmor = plusPhysicArmor;
	}

	public double getPlusMagicArmor() {
		return plusMagicArmor;
	}

	public void setPlusMagicArmor(double plusMagicArmor) {
		this.plusMagicArmor = plusMagicArmor;
	}

	public double getPlusHPgen() {
		return plusHPgen;
	}

	public void setPlusHPgen(double plusHPgen) {
		this.plusHPgen = plusHPgen;
	}

	public double getPlusMPgen() {
		return plusMPgen;
	}

	public void setPlusMPgen(double plusMPgen) {
		this.plusMPgen = plusMPgen;
	}
}
