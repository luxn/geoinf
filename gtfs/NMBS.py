from gtfslib.dao import Dao

# DAO ist ein Data Access Object
dao = Dao("gtfs-nmbs.db.sqlite")
dao.load_gtfs("Feeds/NMBS_belgium.zip")

    