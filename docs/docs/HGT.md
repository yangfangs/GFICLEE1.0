#Example
 
Exploring gene function by GFICLEE  with remove or revise HGT events.

# Download test data

* The test data contains:

| File Name              | genes/species |Description                                               |
|:---------------------- |:--------------|:-------------------------------------------------------- |
|  input_gene_set.txt    |    17         |The gene set from human pathway Nucleotide excision repair|
|  human_profile.txt     |    2620       |This phylogenetic profile includes 2620 genes.            |
|  species_tree_138.nwk  |    138        |The Species tree includes 138 Organisms.                  |
|  138_Taxa.txt          |    138        |The Taxonomy file of 138 species.                         |


Download test data : [TestData2](https://github.com/yangfangs/GFICLEE1.0/blob/master/TestData/TestData2.tar.gz?raw=true)


# Uncompress

```angular2html
tar -zxvf TestData2.tar.gz
```

```angular2html
cd TestData
```

# Run GFICLEE with "-rm" option.

```bash

$ java -jar GFICLEE1.0.jar -i input_gene_set.txt -p human_profile.txt -t species_tree_138.nwk -rm 138_Taxa.txt -o output_file.txt

GFICLEE: Started with arguments: -i input_gene_set.txt -p human_profile.txt -t species_tree_138.nwk -rm 138_Taxa.txt -o output_file.txt
Read Taxonomy file completed...
Found 37 animals
Found 16 plants
Found 56 fungi
Found 29 protists

Detecting the suspicious HGT event...
Detecting suspicious HGT by animals subgroup: 810 genes
Detecting suspicious HGT by plants subgroup: 936 genes
Detecting suspicious HGT by fungi subgroup: 1142 genes
Detecting suspicious HGT by protists subgroup: 1413 genes
Total infer suspicious HGT: 3732 genes
Warning: removing 3732 genes from the phylogenetic profile.

GFICLEE: Completed successfully
Time used: 5.59 Seconds

```

# Run GFICLEE with "-rv" option.

```bash
$ java -jar GFICLEE1.0.jar -i input_gene_set.txt -p human_profile.txt -t species_tree_138.nwk -rv 138_Taxa.txt -o output_file.txt

GFICLEE: Started with arguments: -i input_gene_set.txt -p human_profile.txt -t species_tree_138.nwk -rv 138_Taxa.txt -o output_file.txt
Read Taxonomy file completed...
Found 37 animals
Found 16 plants
Found 56 fungi
Found 29 protists

Detecting the suspicious HGT event...
Detecting suspicious HGT by animals subgroup: 810 genes
Detecting suspicious HGT by plants subgroup: 936 genes
Detecting suspicious HGT by fungi subgroup: 1142 genes
Detecting suspicious HGT by protists subgroup: 1413 genes
Total infer suspicious HGT: 3732 genes
Warning: revising 3732 genes in the phylogenetic profile.

GFICLEE: Completed successfully
Time used: 6.941 Seconds

```

> Notice: using the filter HGT option, users should prepare the Taxonomy's file (split by Tab) consistent with the species tree.
> For example, The "138_Taxa.txt " file that queries the taxonomy from [KEGG organism list](http://rest.kegg.jp/list/organism).
