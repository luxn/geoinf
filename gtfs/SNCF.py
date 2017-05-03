from gtfslib.dao import Dao

# DAO ist ein Data Access Object
dao = Dao("gtfs-sncf.db.sqlite")
dao.load_gtfs("Feeds/SNCF_france.zip")

    