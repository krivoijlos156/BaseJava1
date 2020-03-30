/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    public void clear() {
        for (int i = 0; i < size(); i++) {
            storage[i]=null;
        }

    }

    public void save(Resume r) {
        storage[size()] = r;
    }

    public Resume get(String uuid) {
        for (int i = 0; i < size(); i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    public void delete(String uuid) {
        for (int i = 0; i < size(); i++) {
            if (storage[i].getUuid().equals(uuid)) {
                while (!(storage[i] == null)) {
                    storage[i] = storage[i + 1];
                    i++;
                }
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] storage1 = new Resume[size()];

        for (int i = 0; i < size(); i++) {
            storage1[i] = storage[i];
        }
        return storage1;
    }

    public int size() {
        //return storage.length;
        int count = 0;
        for (int i = 0; i < 10000; i++) {
            if (storage[i] == null) {
                break;
            }
            count++;
        }
        return count;
    }
}
