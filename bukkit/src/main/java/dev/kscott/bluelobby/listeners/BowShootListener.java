package dev.kscott.bluelobby.listeners;

import com.destroystokyo.paper.ParticleBuilder;
import com.google.inject.Inject;
import dev.kscott.bluelobby.items.Item;
import dev.kscott.bluelobby.items.ItemService;
import dev.kscott.bluelobby.items.type.GrapplingBowItem;
import dev.kscott.bluelobby.items.type.SpearItem;
import dev.kscott.bluelobby.items.type.TrackingArrowItem;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BowShootListener implements Listener {

    private final @NonNull ItemService itemService;

    private final @NonNull NamespacedKey trackingArrowBounceKey;

    @Inject
    public BowShootListener(final @NonNull ItemService itemService,
                            final @NonNull JavaPlugin plugin) {
        this.itemService = itemService;
        this.trackingArrowBounceKey = new NamespacedKey(plugin, "tracking_arrow_bounces");
    }

    @EventHandler
    public void onBowShoot(final @NonNull ProjectileLaunchEvent event) {
        final @NonNull Projectile projectile = event.getEntity();
        final @NonNull ProjectileSource shooter = projectile.getShooter();

        if (shooter instanceof Player player) {
            final @Nullable ItemStack itemStack = player.getInventory().getItemInMainHand();

            if (itemStack == null) {
                return;
            }

            final @Nullable Item item = this.itemService.item(itemStack);

            if (item == null) {
                return;
            }

            if (item instanceof GrapplingBowItem) {
                projectile.addPassenger(player);
            }

            if (item instanceof SpearItem) {
                projectile.addPassenger(player);
            }

        }
    }

    @EventHandler
    public void onBowShoot(final @NonNull EntityShootBowEvent event) {
        final @NonNull Entity projectile = event.getProjectile();

        if (projectile instanceof Arrow arrow) {
            System.out.println("is arrow");

            System.out.println("arrow shot location: "+arrow.getLocation());
            System.out.println("arrow shot velocity: "+arrow.getVelocity());
            System.out.println("arrow shot direciton: "+arrow.getLocation().getDirection());

            final @NonNull ItemStack consumedStack = event.getConsumable();

            if (consumedStack.hasItemMeta()) {
                final @NonNull ItemMeta consumedMeta = consumedStack.getItemMeta();

                final @Nullable String id = consumedMeta.getPersistentDataContainer().get(Item.KEY_ITEM_ID, PersistentDataType.STRING);

                final @Nullable Item item = itemService.item(id);

                if (item != null) {
                    if (item instanceof TrackingArrowItem) {
                        projectile.getPersistentDataContainer().set(this.trackingArrowBounceKey, PersistentDataType.INTEGER, 4);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onArrowLand(final @NonNull ProjectileHitEvent event) {
        final @NonNull Projectile projectile = event.getEntity();

        if (!projectile.getPassengers().isEmpty()) {
            final @NonNull List<Entity> passengers = new ArrayList<>(projectile.getPassengers());

            for (final @NonNull Entity passenger : passengers) {
                projectile.removePassenger(passenger);
            }
        }

        if (projectile instanceof Arrow arrow) {
            final var data = projectile.getPersistentDataContainer();

            if (data.has(this.trackingArrowBounceKey, PersistentDataType.INTEGER)) {
                int bounces = data.get(this.trackingArrowBounceKey, PersistentDataType.INTEGER);

                if (bounces == 0) {
                    projectile.getWorld().createExplosion(projectile.getLocation(), 0, false, false);
                    projectile.remove();
                }

                if (bounces > 0) {
                    final @NonNull Location oldLocation = projectile.getLocation();
                    final @NonNull Location location = new Location(oldLocation.getWorld(),
                            oldLocation.getX(),
                            oldLocation.getY() + 1,
                            oldLocation.getZ(),
                            oldLocation.getYaw(),
                            0);

                    final @Nullable BlockFace face = event.getHitBlockFace();

                    if (face == null) return;


                    final @NonNull Vector a = oldLocation.getDirection();
                    final @NonNull Vector b = getNormal(face);

                    final @NonNull Vector direction = a.subtract(b.multiply(a.dot(b)).multiply(2));


                    final @NonNull Arrow newArrow = location.getWorld().spawnArrow(
                            location,
                            direction,
                            0.6F,
                            12
                    );

                    newArrow.getPersistentDataContainer().set(this.trackingArrowBounceKey, PersistentDataType.INTEGER, bounces - 1);

                    arrow.remove();
                }
            }
        }
    }

    public static @NonNull Vector getNormal(BlockFace face) {
        switch (face) {
            case UP: return new Vector(0, 1, 0);
            case DOWN: return new Vector(0, -1, 0);

            case NORTH: return new Vector(0, 0, -1);
            case SOUTH: return new Vector(0, 0, 1);

            case EAST: return new Vector(-1, 0, 0);
            case WEST: return new Vector(1, 0, 0);

            default: return new Vector(0, 0, 0);
        }
    }
}
