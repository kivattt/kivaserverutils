- Better system for configuration, less confusing commands (something like /gamerule <key> <value>) 
- In `/spawn`, `/teleport` command check if the target chunk is loaded by the player
and if not, warn them to try relogging if they're stuck in air

- Improve the 100% CPU fix to not sleep on server close (saving chunks?)

# Chest/crate logging
Username in the logging

More descriptive logging?
- Itemstack amount changed
- Itemstack added
- Itemstack removed

# Kiva config
Just make it thought-out, not requiring `...disabled` to be the default
just have something like
```
mobcap=true
homecommands=false
```