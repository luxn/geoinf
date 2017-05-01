# -*- coding: utf-8 -*-
"""
Created on Tue Apr 25 20:34:13 2017

@author: luxn
"""

import sqlite3
import argparse



if __name__ == "__main__":

    parser = argparse.ArgumentParser(description="Turn GTFS.txt into SQLite DB")
    parser.add_argument('gtfs_path', type=str, help="the path to the gtfs feed *.txt")
    parser.add_argument('sqlite_path', type=str, help="sqlite db path")

    args = parser.parse_args()

    conn = sqlite3.connect(args.sqlite_path)



    
       

   
    
