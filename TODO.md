- Make container logging queued in a thread to make it less spammy, with handling on server close

- Better system for configuration, less confusing commands (something like /gamerule \<key> \<value>)

- Chat messages stored in a file with the normal Minecraft language format string system

- Clean up the `/*list` and `/*listall` commands with optional "all" argument, so it would be `/*list all`. Alternatively do an alias with an if for it

- In `/spawn`, `/teleport`, `/home` commands check if the target chunk is loaded by the player
and if not, warn them to try relogging if they're stuck in air

- Dimension support for spawning (respawning/first join) in the nether
Calculate the actual spawn point since it's probably chunk-based and just translated back to normal coords which aren't the exact same\
prob need some explanation to the user about it

- Improve the 100% CPU fix to not sleep on server close (saving chunks?)

# Kiva config
Just make it thought-out, not requiring `...disabled` to be the default
just have something like
```
mobcap=true
homecommands=false
```