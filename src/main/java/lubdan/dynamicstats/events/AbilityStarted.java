package lubdan.dynamicstats.events;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.event.AbilityStartEvent;
import com.projectkorra.projectkorra.firebending.lightning.Lightning;
import com.projectkorra.projectkorra.waterbending.multiabilities.WaterArmsSpear;
import lubdan.dynamicstats.Dynamicstats;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AbilityStarted implements Listener {

 private Dynamicstats plugin;
 private float multibending;
 private float avatar;
 private boolean multispear;

    public AbilityStarted(Dynamicstats instance){
     this.plugin = instance;
     this.multibending = Float.valueOf(plugin.getConfig().getString("Multibending-Multipler"));
     this.avatar = Float.valueOf(plugin.getConfig().getString("Avatar-Multipler"));
     this.multispear = plugin.getConfig().getBoolean("Avatar-Spear-Toggle");
    }

    @EventHandler
    private void onUse(AbilityStartEvent event){
        if(event.getAbility().getName().equals("Lightning")){
            Player player = event.getAbility().getPlayer();
            BendingPlayer bplayer = BendingPlayer.getBendingPlayer(player);
            if(bplayer.getElements().size() <= 3 && bplayer.getElements().size() != 1 && !bplayer.isAvatarState()){
                Lightning lightning = (Lightning) event.getAbility();
                lightning.setDamage(lightning.getDamage() * this.multibending);
                lightning.setRange(lightning.getRange() * this.multibending);
            }
            if(bplayer.getElements().size() >= 4){
                Lightning lightning = (Lightning) event.getAbility();
                lightning.setDamage(lightning.getDamage() * this.avatar);
                lightning.setRange(lightning.getRange() * this.avatar);
            }
        }
        if(event.getAbility().getName().equals("WaterArms")){
            Player player = event.getAbility().getPlayer();
            BendingPlayer bplayer = BendingPlayer.getBendingPlayer(player);
            if(bplayer.getBoundAbilityName().contains("Spear")){
                WaterArmsSpear spear = (WaterArmsSpear) event.getAbility();
                if(bplayer.getElements().size() > 2 && !bplayer.isAvatarState() && this.multispear){
                    spear.setSpearSphere(0);
                }
            }
        }
        if(event.getAbility().getName().equals("EarthArmor")){
            Player player = event.getAbility().getPlayer();
            BendingPlayer bplayer = BendingPlayer.getBendingPlayer(player);
            if(bplayer.getElements().size() > 1 && !bplayer.isAvatarState()){
                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        float baseabsorp = GeneralMethods.getAbsorbationHealth(player);
                        if(bplayer.getElements().size() == 2){
                            GeneralMethods.setAbsorbationHealth(event.getAbility().getPlayer(),baseabsorp * multibending);
                        }
                        if(bplayer.getElements().size() > 2){
                            GeneralMethods.setAbsorbationHealth(event.getAbility().getPlayer(),baseabsorp * avatar);

                        }
                    }
                },20);
            }
        }
    }





}
