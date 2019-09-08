# Docker notes and shortcuts

## Dependencies

You will only need docker and docker-compose to run the application.

* docker (>= 17.12.0)
* docker-compose (>= 1.18)

## Installation

### Clone repository

```
git clone https://github.com/golf-clash-notebook/golf-clash-notebook.github.io.git
```

##### Important Windows Note

To avoid any line ending issues in a Windows environment, add `--config core.autocrlf=input` config flag to the clone command.

```
git clone https://github.com/golf-clash-notebook/golf-clash-notebook.github.io.git --config core.autocrlf=input
```

### Build the container

Run `bin/dev build` to build the container, then `bin/dev bash` to enter the container.

### Build the site

From inside the container, run `sbt`, then run `site/makeMicrosite` to generate the site.

### Run the server

Run `bin/dev server` to start the Jekyll server and navigate to `localhost:4000` to see the site.

## Using bin/dev shortcuts

Two helper scripts are available in the `/bin` folder, `dev` for linux environments (including inside the container), and `dev.ps1` for Windows environments (using Powershell). The scripts provide a set of shortcuts for commonly issued commands in the form of `bin/dev [command]`.

### Initial build or rebuild the web container

```
bin/dev build
```

### Run the web container and start bash shell

```
bin/dev bash
```

### Run the web container and start the Jekyll server

```
bin/dev up
```

### Enter the running web container from a new terminal

```
bin/dev enter
```

### Shortcut for `site/makeMicroSite` to regen after a change

```
bin/dev make
```

### Run the Jekyll server from within the container

```
bin/dev server
```
