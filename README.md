# WorldGuardTitles
Send title messages on entry/exit for regions. 

### [Download] (https://github.com/cyberpwnn/WorldGuardTitles/releases)

# Configuration
``` YAML
title:
  # Configure the title message (color codes accepted) Use {RG} for the region name
  entry: '&aEntered &b{RG}'
  exit: '&cExited &e{RG}'
  
  timings:
    # Fade In/out and stay time (in ticks) 20 ticks = 1 second
    fade-in-ticks: 10
    fade-out-ticks: 20
    stay-ticks: 30
  
  format:
    #World guard does not retain capitals. Should we capitalize it?
    capitalize-region-name: true

```

# Permissions
```
worldguardtitles.reload  -  Permission to reload the world guard title config
```

# Commands
```
/wgt reload  - WGT is an alias for worldguardtitles
/worldguardtitles reload  - Reloads the configuration
```
