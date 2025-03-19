package app.entities;

public class LifehackTeam08UserUpgrades {
    private int userId;
    private int upgradeId;
    private int tier;

    public LifehackTeam08UserUpgrades(int userId, int upgradeId, int tier){
        this.userId = userId;
        this.upgradeId = upgradeId;
        this.tier = tier;

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUpgradeId() {
        return upgradeId;
    }

    public void setUpgradeId(int upgradeId) {
        this.upgradeId = upgradeId;
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    @Override
    public String toString(){
        return "LifehackTeam08Upgrades{" + "userId=" + userId + ", upgradeId=" + upgradeId + ", tier=" + tier + '}';
    }
}
