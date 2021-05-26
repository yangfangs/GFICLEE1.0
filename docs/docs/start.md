#Getting start
**GFICLEE** is implemented in Java programming language, Supporting **Linux**, **Windows**, and **Mac os** platforms.

## Install 


###  install Java

**GFICLEE** support Java8 or late

* For Fedora/Cent OS

```angular2html
sudo dnf install java-1.8.0-openjdk
```

* For Ubuntu/Debian

```angular2html
sudo apt-get install default-jre
```



### Downloading GFICLEE

[Download GFICLEE 1.0](https://github.com/yangfangs/GFICLEE1.0/blob/master/GFICLEE1.0.jar?raw=true)


## Usage

**GFICLEE** is a command line program, should execute in console or shell.


```angular2html
java -jar GFICLEE1.0.jar -i input_gene_set.txt -p phylogenetic_profile.txt -t species_tree.nwk -o output_file.txt
```


## Basic options

| Option |  Description                                                                                |
|:------- |:-------------------------------------------------------------------------------------------|
|  -i     |  Input gene set in the same pathway or complex.                                            |
|  -p     |  The binary phylogenetic profile.                                                          |
|  -o     |  Result file Path.                                                             |
|  -t     |  The species tree in Newick format.                                                         |
|  -c     |  Predicted with multi threads. The default is 1.                                           |
|  -m     |  Choose predict model<'eq','h','l','lh'>. the default model is 'eq'.                       |
|  -rm    |  Remove the suspicious HGT events in phylogenetic profile (need provide taxonomy file).    |
|  -rv    |  Revise the suspicious HGT events from phylogenetic profile (need provide taxonomy file).  |




