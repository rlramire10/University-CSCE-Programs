
//Edited By: Rudy Ramirez
//Date: 9/30/21
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NewText implements Writable,WritableComparable<NewText> {

    private Text word;

    public NewText(Text word) {
        this.word = word;
    }

    public NewText(String word) {
        this(new Text(word));
    }

    public NewText() {
        this.word = new Text();
    }

    //Edited CompareTo Method Results in Sorting WordPairs In Reverse Alphabetical Order
	@Override
    public int compareTo(NewText other) {
		int returnVal = this.word.compareTo(other.get());
		return -1 * returnVal;
    }

    public static NewText read(DataInput in) throws IOException {
        NewText newtext = new NewText();
        newtext.readFields(in);
        return newtext;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        word.write(out);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        word.readFields(in);
    }

    @Override
    public String toString() {
        return word+": ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewText newtext = (NewText) o;

        if (word != null ? !word.equals(newtext.word) : newtext.word != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (word != null) ? word.hashCode() : 0;
        return result;
    }

    public void set(String word){
        this.word.set(word);
    }
	
    public Text get() {
        return word;
    }
}