#Example

A short tutorial below explains how to run GFICLEE in command line.
    
# Download test data

* The test data contains:

| File Name              | genes/species |Description                                               |
|:---------------------- |:--------------|:-------------------------------------------------------- |
|  input_gene_set.txt    |    9          |The gene set from human pathway Nucleotide excision repair|
|  human_profile.txt     |    2620       |This phylogenetic profile includes 2000 human genes.      |
|  species_tree_111.nwk  |    111        |The Species tree includes 111 Organisms                   |


Download test data : [TestData](https://github.com/yangfangs/GFICLEE1.0/blob/master/TestData/TestData.tar.gz?raw=true)


# Uncompress

```angular2html
tar -zxvf TestData.tar.gz
```

```angular2html
cd TestData
```

# Run GFICLEE


```angular2html

$ java -jar GFICLEE1.0.jar -i input_gene_set.txt -p human_profile.txt -t species_tree_111.nwk -o output_file.txt
GIFICLEE: Started with arguments: -i input_gene_set.txt -p human_profile.txt -t species_tree_111.nwk -o output_file.txt
GIFCLEE: Completed successfully
Time used: 0 Seconds


```