package dev.idx0;

import lombok.extern.java.Log;

import java.time.LocalTime;
import java.util.*;

@Log
public class Cache {
    private List<CacheItem> cacheItems = new LinkedList<>();

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

    public  void  set(DNSMessageResourceRecord rr) {
        long current = getCurrentTimeStamp();
        CacheItem newItem = new CacheItem(rr, rr.getTimeToLive() +current);
        Iterator<CacheItem> iter = cacheItems.iterator();
        while (iter.hasNext()){

            CacheItem item = iter.next();
            if (item.getInvalidTimeStamp() < current) {
                iter.remove();
                continue;
            }
            DNSMessageResourceRecord itemRR = item.getResourceRecord();
            if (itemRR.getName() != null && itemRR.getName().equals(rr.getName()) &&
                    itemRR.getQuestionClass() == rr.getQuestionType() &&
                    itemRR.getQuestionClass() == rr.getQuestionClass()){
                // cache update !
                cacheItems.remove(item);
                break;
            }
        }
        cacheItems.add(newItem);

    }

    public  List<DNSMessageResourceRecord> get(DNSQuestion q) {
        long current = getCurrentTimeStamp();
        List<DNSMessageResourceRecord> result = new ArrayList<>();
        Iterator<CacheItem> iter = cacheItems.iterator();
        while (iter.hasNext()){
            CacheItem item = iter.next();
            if (item.getInvalidTimeStamp() < current) {
                iter.remove();
                continue;
            }
            DNSMessageResourceRecord rr = item.getResourceRecord();
            if (q.getName().equals(rr.getName()) &&
                    q.getQuestionClass() == rr.getQuestionType() &&
                    q.getQuestionClass() == rr.getQuestionClass()) {
                // cache hit
                log.info("缓存命中了！");
                rr.setTimeToLive(item.getInvalidTimeStamp() - current);
                result.add(rr);
            }
        }
        return result;
    }
}
