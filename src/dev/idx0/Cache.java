package dev.idx0;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Cache {
    private List<CacheItem> cacheItems = new ArrayList<>();

    class CacheItem {
        private DNSMessageResourceRecord resourceRecord;

        public CacheItem(DNSMessageResourceRecord resourceRecord, long invalidTimeStamp) {
            this.resourceRecord = resourceRecord;
            this.invalidTimeStamp = invalidTimeStamp;
        }

        private long invalidTimeStamp;

        /**
         * Gets resourceRecord
         *
         * @return value of resourceRecord
         */
        public DNSMessageResourceRecord getResourceRecord() {
            return resourceRecord;
        }

        /**
         * Set the value of resourceRecord
         *
         * @return CacheItem
         */
        public CacheItem setResourceRecord(DNSMessageResourceRecord resourceRecord) {
            this.resourceRecord = resourceRecord;
            return this;
        }

        /**
         * Gets invalidTimeStamp
         *
         * @return value of invalidTimeStamp
         */
        public long getInvalidTimeStamp() {
            return invalidTimeStamp;
        }

        /**
         * Set the value of invalidTimeStamp
         *
         * @return CacheItem
         */
        public CacheItem setInvalidTimeStamp(long invalidTimeStamp) {
            this.invalidTimeStamp = invalidTimeStamp;
            return this;
        }
    }

    protected long getCurrentTimeStamp() {
        return System.currentTimeMillis() / 1000;

    }

    public void set(DNSMessageResourceRecord rr) {
        long current = getCurrentTimeStamp();
        CacheItem new_item = new CacheItem(rr, rr.getTimeToLive() +current);
        for (CacheItem item : cacheItems) {
            if (item.getInvalidTimeStamp() < current) {
                cacheItems.remove(item);
                continue;
            }
            DNSMessageResourceRecord item_rr = item.getResourceRecord();
            if (item_rr.getName().equals(rr.getName()) &&
                    item_rr.getQuestionClass() == rr.getQuestionType() &&
                    item_rr.getQuestionClass() == rr.getQuestionClass()){
                // cache update !
                cacheItems.remove(item);
                break;
            }
        }
        cacheItems.add(new_item);

    }

    public List<DNSMessageResourceRecord> get(DNSQuestion q) {
        long current = getCurrentTimeStamp();
        List<DNSMessageResourceRecord> result = new ArrayList<>();
        for (CacheItem item : cacheItems) {
            if (item.getInvalidTimeStamp() < current) {
                cacheItems.remove(item);
                continue;
            }
            DNSMessageResourceRecord rr = item.getResourceRecord();
            if (q.getName().equals(rr.getName()) &&
                    q.getQuestionClass() == rr.getQuestionType() &&
                    q.getQuestionClass() == rr.getQuestionClass()) {
                // cache hit
                rr.setTimeToLive(item.getInvalidTimeStamp() - current);
                result.add(rr);
            }
        }
        return result;
    }
}
