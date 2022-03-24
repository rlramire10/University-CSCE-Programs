
//Edited By: Rudy Ramirez
//Date: 10/23/21
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class wordpair implements Writable,WritableComparable<wordpair> {

    private Text key;
    private Text values;

    public wordpair(Text key, Text values) {
        this.key = key;
        this.values = values;
    }

    public wordpair(String key, String values) {
        this(new Text(key),new Text(values));
    }

    public wordpair() {
        this.key = new Text();
        this.values = new Text();
    }

    @Override
    public int compareTo(wordpair other) {                         // A compareTo B
        int returnVal = this.key.compareTo(other.getKey());      // return -1: A < B
        if(returnVal != 0){                                        // return 0: A = B
            return returnVal;                                      // return 1: A > B
        }
        if(this.values.toString().equals("*")){
            return -1;
        }else if(other.getValues().toString().equals("*")){
            return 1;
        }
        return this.values.compareTo(other.getValues());
    }

    public static wordpair read(DataInput in) throws IOException {
        wordpair wordPair = new wordpair();
        wordPair.readFields(in);
        return wordPair;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        key.write(out);
        values.write(out);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        key.readFields(in);
        values.readFields(in);
    }

    @Override
    public String toString() {
        return key + "   " + values + ":";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        wordpair wordPair = (wordpair) o;

        if (values != null ? !values.equals(wordPair.values) : wordPair.values != null) return false;
        if (key != null ? !key.equals(wordPair.key) : wordPair.key != null) return false;

        return true;
    }
	
    @Override
    public int hashCode() {
        int result = (key != null) ? key.hashCode() : 0;
        result = 163 * result;
		return result;
    }

    public void setKey(String key){
        this.key.set(key);
    }
    public void setValues(String values){
        this.values.set(values);
    }

    public Text getKey() {
        return key;
    }

    public Text getValues() {
        return values;
    }
}