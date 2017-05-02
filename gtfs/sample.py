# -*- coding: utf-8 -*-
"""
Created on Tue May  2 21:23:46 2017

@author: luxn
"""

from gtfslib.dao import Dao

# DAO ist ein Data Access Object
dao = Dao("gtfs-sample.db.sqlite")

for stop in dao.stops():
    print(stop.stop_name)
    
"""
So eine Ausgabe sollte dann kommen:


(C:\Anaconda3) D:\Github\geoinf\gtfs>python sample.py
    Amargosa Valley (Demo)
    Nye County Airport (Demo)
    Bullfrog (Demo)
    Doing Ave / D Ave N (Demo)
    E Main St / S Irving St (Demo)
    Furnace Creek Resort (Demo)
    North Ave / D Ave N (Demo)
    North Ave / N A Ave (Demo)
    Stagecoach Hotel & Casino (Demo)

"""
