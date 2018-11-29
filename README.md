
### Golf Clash Notebook

A website with a collection of useful resources & tools to help people with Golf Clash.

### Development Workflow

**Prerequisites**
1. JDK 8+
1. Some kind of simple HTTP server such as Jekyll or SimpleHTTPServer (python)

**Building**
```bash
> sbt
> site/makeMicrosite  # Anytime you edit a source file, repeat this command to see updates in your browser
```

In a separate terminal in the notebook top level directory (using jekyll):

```bash
> jekyll serve -s modules/site/target/site
# Should be able to see the site in your browser at: `localhost:4000`
```
