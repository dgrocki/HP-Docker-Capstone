#!/bin/bash
cp ../Brendan/makefile .
make >/dev/null
make
rm -f makefile *.ps *.dvi *.out *.log *.aux *.bbl *blg *.pyg *.toc
