# GFICLEE: Gene Function Inferred by Common Loss Evolutionary Events.


GFICLEE is a program for predicting the new members in biological pathway or biological complex. 
It is based on the common loss events in gene evolution. When input a gene set in same biological pathway, 
this program will scan all reference genome predict potential members of this pathway. 
This program requires three inputs, (1) the gene set in same biological pathway or biological complex, 
(2)a binary species tree, (3) the binary phylogenetic profile of all genes in this reference genome across the species.


![Workflow](https://github.com/yangfangs/GFICLEE1.0/blob/master/TestData/Rplot_all_hamming_loo.png)


## Basic options

| option |  Description                                         |
|:------- |:--------------------------------------------------- |
|  -i     |  Input gene set in the same pathway or complex.     |
|  -p     |  The binary phylogenetic profile.                   |
|  -o     |  The predict result file name.                      |
|  -t     |  The species tree with nwk format.                  |
|  -c     |  Predicted with multi threads. The default is 1.                  |
|  -m     |  Choose predict model<'eq','h','l','lh'>. the default model is 'eq' |


## Usage

```bash

java -jar GFICLEE1.0.jar -i input_gene_set.txt -p phylogenetic_profile.txt -t species_tree.nwk -o output_file.txt

```

**Detail document** : [https://yangfangs.github.io/GFICLEE1.0/](https://yangfangs.github.io/GFICLEE1.0/)