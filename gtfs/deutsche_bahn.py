from gtfslib.dao import Dao

# DAO ist ein Data Access Object
dao = Dao("gtfs-deutsche_bahn.db.sqlite")
dao.load_gtfs("Feeds/DB_germany.zip")

    