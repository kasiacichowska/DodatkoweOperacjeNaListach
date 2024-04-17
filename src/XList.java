import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class XList<T> implements List{
    private List<T> list;

    public XList(T... elements) {
        this.list = new ArrayList<T>(Arrays.asList(elements));
    }

    public XList(Collection<T> elements) {
        this.list = new ArrayList<>(elements);
    }

    public XList() {
        this.list = new ArrayList<>();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public boolean add(Object o) {
        return list.add((T) o);
    }

    @Override
    public boolean remove(Object o) {
        return list.remove((T) o);
    }

    @Override
    public boolean addAll(Collection c) {
        return list.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection c) {
        return list.addAll(index, c);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public Object get(int index) {
        return list.get(index);
    }

    @Override
    public Object set(int index, Object element) {
        return list.set(index, (T) element);
    }

    @Override
    public void add(int index, Object element) {
        list.add(index, (T) element);
    }

    @Override
    public Object remove(int index) {
        return list.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public ListIterator listIterator() {
        return list.listIterator();
    }

    @Override
    public ListIterator listIterator(int index) {
        return list.listIterator(index);
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }

    @Override
    public boolean retainAll(Collection c) {
        return list.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection c) {
        return list.removeAll(c);
    }

    @Override
    public boolean containsAll(Collection c) {
        return list.containsAll(c);
    }

    @Override
    public Object[] toArray(Object[] a) {
        return list.toArray(a);
    }

    public static <T> XList<T> of(T... elements) {
        return new XList<>(elements);
    }

    public static <T> XList<T> of(Collection<T> elements) {
        return new XList<>(elements);
    }

    public static XList<String> charsOf(String s) {
        List<String> characterList = new ArrayList<>();
        for (char ch : s.toCharArray()) {
            characterList.add(String.valueOf(ch));
        }
        return new XList<>(characterList);
    }

    public static XList<String> tokensOf(String s, String delim) {
        return new XList<>(Arrays.asList(s.split(delim)));
    }

    public static XList<String> tokensOf(String s) {
        return tokensOf(s, " ");
    }

    public XList<T> union(Collection<T> c) {
        XList<T> xList = new XList<>(this.list);
        xList.list.addAll(c);
        return xList;
    }

    public XList<T> union(T... elements) {
        return union(Arrays.asList(elements));
    }

    public XList<T> diff(Collection<T> c) {
        XList<T> xList = new XList<>(this.list);
        xList.list.removeAll(c);
        return xList;
    }

    public XList<T> unique() {
        LinkedHashSet<T> set = new LinkedHashSet<>(this.list);
        return new XList<>(set);
    }

    private void combine(int index, List<String> current, List<XList<String>> result) {
        if (index < 0) {
            result.add(new XList<>(new ArrayList<>(current)));
            return;
        }

        List<String> currentList = (List<String>) list.get(index);
        for (String element : currentList) {
            current.add(element);
            combine(index - 1, current, result);
            current.remove(current.size() - 1);
        }
    }

    public XList<XList<String>> combine(){
        List<XList<String>> combined = new ArrayList<>();
        combine(list.size() - 1, new ArrayList<>(), combined);

        for (XList<String> elem : combined) {
            Collections.reverse(elem);
        }
        return new XList<>(combined);
    }

    public <U> XList<U> collect(Function<T, U> f){
        XList<U> xList = new XList<>();
        for(T t : this.list){
            xList.add(f.apply(t));
        }
        return xList;
    }

    public String join(String delim){
        StringBuilder sb = new StringBuilder();
        for(T t : this.list){
            sb.append(t.toString());
            sb.append(delim);
        }
        sb.delete(sb.length() - delim.length(), sb.length());
        return sb.toString();
    }

    public String join(){
        return join("");
    }

    public void forEachWithIndex(BiConsumer<T, Integer> f){
        for(int i = 0; i < this.list.size(); i++){
            f.accept(this.list.get(i), i);
        }
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
