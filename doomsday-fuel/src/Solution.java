// Thanks to all guys in this topic
// https://stackoverflow.com/questions/62671372/google-foo-bar-challenge-level-3-doomsday-fuel
import java.lang.Math;
import java.util.ArrayList;

public class Solution {
  public static int[] solution(int[][] m) {
    ArrayList<Integer> termStateList = new ArrayList<>();
    ArrayList<Integer> nonTermStateList = new ArrayList<>();
    ArrayList<Integer> stateDenominatorList = new ArrayList<>();
    int[] r = new int[2];
    if (m[0][0] == 0 && m.length == 1) {
      r[0] = 1;
      r[1] = 1;
      return r;
    }
    for (int i = 0; i < m.length; i++) {
      boolean allZeroInState = true;
      int stateDenominatorTemp = 0;
      for (int j = 0; j < m[0].length; j++) {
        if (m[i][j] != 0) {
          allZeroInState = false;
          stateDenominatorTemp += m[i][j];
        }
      }
      if (allZeroInState) {
        termStateList.add(i);
      } else {
        nonTermStateList.add(i);
        stateDenominatorList.add(stateDenominatorTemp);
      }
    }
    Fraction one = new Fraction(1);
    Fraction zero = new Fraction(0);
    ArrayList<ArrayList<Fraction>> IList = new ArrayList<>();
    for (int i = 0; i < nonTermStateList.size(); i++) {
      ArrayList<Fraction> IRow = new ArrayList<>();
      for (int j = 0; j < nonTermStateList.size(); j++) {
        if (i == j) {
          IRow.add(one);
        } else {
          IRow.add(zero);
        }
      }
      IList.add(IRow);
    }
    Matrix I = new Matrix(IList, nonTermStateList.size(), nonTermStateList.size());
    ArrayList<ArrayList<Fraction>> QList = new ArrayList<>();
    for (int i = 0; i < nonTermStateList.size(); i++) {
      ArrayList<Fraction> QRow = new ArrayList<>();
      for (Integer integer : nonTermStateList) {
        QRow.add(new Fraction(m[nonTermStateList.get(i)][integer], stateDenominatorList.get(i)));
      }
      QList.add(QRow);
    }

    Matrix Q = new Matrix(QList, nonTermStateList.size(), nonTermStateList.size());

    // Create R
    ArrayList<ArrayList<Fraction>> RList = new ArrayList<>();
    for (int i = 0; i < nonTermStateList.size(); i++) {
      ArrayList<Fraction> RRow = new ArrayList<>();
      for (Integer integer : termStateList) {
        RRow.add(new Fraction(m[nonTermStateList.get(i)][integer], stateDenominatorList.get(i)));
      }
      RList.add(RRow);
    }

    Matrix R = new Matrix(RList, nonTermStateList.size(), termStateList.size());

    // Find I - Q
    Matrix IminusQ = I.minus(Q);

    // Find F = (I - Q)^-1
    Matrix F = IminusQ.getInverseMatrix();

    // Find FR
    Matrix FR = F.multiply(R);

    // Take the first row of FR
    ArrayList<Fraction> FRRow = FR.getRow(0);
    ArrayList<Fraction> numeratorList = new ArrayList<>(); // numeratorList
    int[] denomList = new int[FRRow.size()]; // denomList

    // Find the numerators and the common denominator, make it an array
    for (int i = 0; i < FRRow.size(); i++) {
      denomList[i] = FRRow.get(i).getDenominator();
      numeratorList.add(FRRow.get(i));
    }
    int lcm = getLcm(denomList);
    int[] result = new int[FRRow.size() + 1];
    if (m[0][0] == 0 && m.length == 1) {
      result[0] = 1;
      result[1] = 1;
    } else {
      for (int j = 0; j < result.length - 1; j++) {
        numeratorList.set(j, numeratorList.get(j).multiply(new Fraction(lcm)));
        result[j] = numeratorList.get(j).getNumerator();
      }
      result[FRRow.size()] = lcm;
    }
    return result;
  }

  private static int getLcm(int[] arr) {
    int max = 0;
    int n = arr.length;
    for (int k : arr) {
      if (max < k) {
        max = k;
      }
    }
    int res = 1;
    int factor = 2;
    while (factor <= max) {
      ArrayList<Integer> arrIndex = new ArrayList<>();
      for (int j = 0; j < n; j++) {
        if (arr[j] % factor == 0) {
          arrIndex.add(arrIndex.size(), j);
        }
      }
      if (arrIndex.size() >= 2) {
        // Reduce all array elements divisible by factor.
        for (Integer index : arrIndex) {
          arr[index] /= factor;
        }

        res *= factor;
      } else {
        factor++;
      }
    }

    // Then multiply all reduced array elements
    for (int j : arr) {
      res *= j;
    }

    return res;
  }

  private static class Matrix {

    private final int M;
    private final int N;
    private final Fraction det;
    private final ArrayList<ArrayList<Fraction>> matrix;
    private final ArrayList<ArrayList<Fraction>> inverseMatrix;

    public Matrix(ArrayList<ArrayList<Fraction>> mat, int m, int n) {
      this.matrix = mat;
      this.M = m;
      this.N = n;
      this.det = this.determinant(mat, n);
      this.inverseMatrix = this.inverse();
    }

    private void getCofactor(
        ArrayList<ArrayList<Fraction>> mat,
        ArrayList<ArrayList<Fraction>> tempMat,
        int p,
        int q,
        int n) {
      int i = 0;
      int j = 0;
      for (int row = 0; row < n; row++) {
        for (int col = 0; col < n; col++) {
          if (row != p && col != q) {
            tempMat.get(i).set(j++, mat.get(row).get(col));
            if (j == n - 1) {
              j = 0;
              i++;
            }
          }
        }
      }
    }

    private Fraction determinant(ArrayList<ArrayList<Fraction>> mat, int n) {
      Fraction ans = new Fraction(0, 1);
      if (this.M != this.N) {
        return ans;
      }
      if (n == 1) {
        return mat.get(0).get(0);
      }
      ArrayList<ArrayList<Fraction>> tempMat = new ArrayList<>();
      // Init 2d fraction arraylist
      for (int i = 0; i < this.M; i++) {
        ArrayList<Fraction> tempMatRow = new ArrayList<>();
        for (int j = 0; j < this.N; j++) {
          tempMatRow.add(new Fraction(0, 1));
        }
        tempMat.add(tempMatRow);
      }

      int sign = 1;
      Fraction signFraction = new Fraction(sign, 1);
      for (int k = 0; k < n; k++) {
        this.getCofactor(mat, tempMat, 0, k, n);
        ans =
            ans.plus(
                signFraction.multiply(mat.get(0).get(k).multiply(determinant(tempMat, n - 1))));
        sign = -sign;
        signFraction = new Fraction(sign, 1);
      }
      return ans;
    }

    private void adjoint(ArrayList<ArrayList<Fraction>> mat, ArrayList<ArrayList<Fraction>> adj) {
      if (this.N == 1) {
        adj.get(0).set(0, new Fraction(1, 1));
        return;
      }
      int sign;

      ArrayList<ArrayList<Fraction>> tempMat = new ArrayList<>();
      // Init 2d fraction arraylist
      for (int i = 0; i < this.N; i++) {
        ArrayList<Fraction> tempMatRow = new ArrayList<>();
        for (int j = 0; j < this.N; j++) {
          tempMatRow.add(new Fraction(0, 1));
        }
        tempMat.add(tempMatRow);
      }

      for (int p = 0; p < this.N; p++) {
        for (int q = 0; q < this.N; q++) {
          this.getCofactor(mat, tempMat, p, q, this.N);
          sign = ((p + q) % 2 == 0) ? 1 : -1;
          Fraction signFraction = new Fraction(sign, 1);
          adj.get(q).set(p, signFraction.multiply((this.determinant(tempMat, this.N - 1))));
        }
      }
    }

    private ArrayList<ArrayList<Fraction>> inverse() {
      ArrayList<ArrayList<Fraction>> inv = new ArrayList<>();
      // Init 2d fraction arraylist
      for (int i = 0; i < this.M; i++) {
        ArrayList<Fraction> invRow = new ArrayList<>();
        for (int j = 0; j < this.N; j++) {
          invRow.add(new Fraction(0, 1));
        }
        inv.add(invRow);
      }

      if (this.det.equals(new Fraction(0))) {
        return inv;
      }

      ArrayList<ArrayList<Fraction>> adj = new ArrayList<>();
      // Init 2d fraction arraylist
      for (int i = 0; i < this.M; i++) {
        ArrayList<Fraction> adjRow = new ArrayList<>();
        for (int j = 0; j < this.N; j++) {
          adjRow.add(new Fraction(0, 1));
        }
        adj.add(adjRow);
      }

      adjoint(this.matrix, adj);
      for (int p = 0; p < this.N; p++) {
        for (int q = 0; q < this.N; q++) {
          Fraction temp = adj.get(p).get(q).dividedBy(this.det);
          inv.get(p).set(q, temp);
        }
      }
      return inv;
    }

    public Matrix getInverseMatrix() {
      return new Matrix(this.inverseMatrix, this.M, this.N);
    }

    public Fraction getElement(int m, int n) {
      return this.matrix.get(m).get(n);
    }

    public ArrayList<Fraction> getRow(int m) {
      if (m <= this.M) {
        return this.matrix.get(m);
      }
      return new ArrayList<>();
    }

    public Matrix minus(Matrix mat) {
      int M_m = mat.getDimension()[0];
      int N_m = mat.getDimension()[1];
      if (this.M != M_m || this.N != N_m) {
        return mat;
      } else {
        ArrayList<ArrayList<Fraction>> difference = new ArrayList<>();
        // Init 2d fraction arraylist
        for (int i = 0; i < this.M; i++) {
          ArrayList<Fraction> differenceRow = new ArrayList<>();
          for (int j = 0; j < this.N; j++) {
            differenceRow.add(new Fraction(0, 1));
          }
          difference.add(differenceRow);
        }
        for (int i = 0; i < this.M; i++) {
          for (int j = 0; j < this.N; j++) {
            difference.get(i).set(j, this.matrix.get(i).get(j).minus(mat.getElement(i, j)));
          }
        }
        return new Matrix(difference, this.M, this.N);
      }
    }

    public Matrix multiply(Matrix mat) {
      // M N M N
      // X(m, n) x Y(n, p) = Z(m, p)
      int M_m = mat.getDimension()[0];
      int p_m = mat.getDimension()[1];
      if (this.N != M_m) {
        return mat;
      } else {
        ArrayList<ArrayList<Fraction>> product = new ArrayList<>();
        for (int i = 0; i < this.M; i++) {
          ArrayList<Fraction> productRow = new ArrayList<>();
          for (int j = 0; j < p_m; j++) {
            productRow.add(new Fraction(0, 1));
          }
          product.add(productRow);
        }
        for (int i = 0; i < this.M; i++) {
          for (int j = 0; j < p_m; j++) {
            for (int k = 0; k < this.N; k++) {
              Fraction temp = product.get(i).get(j);
              product
                  .get(i)
                  .set(j, temp.plus(this.matrix.get(i).get(k).multiply(mat.getElement(k, j))));
            }
          }
        }
        return new Matrix(product, this.M, p_m);
      }
    }

    public int[] getDimension() {
      return new int[] {this.M, this.N};
    }
  }

  private static class Fraction {

    private int numerator;
    private int denominator = 1;
    private boolean sign = false; // true = negative, false = positive

    public Fraction(int num, int denom) {
      this.numerator = num;
      if (denom != 0) {
        this.denominator = denom;
      }

      this.simplify();
    }

    public Fraction(int num) {
      this.numerator = num;
      this.simplify();
    }

    private int getGcm(int num1, int num2) {
      return num2 == 0 ? num1 : this.getGcm(num2, num1 % num2);
    }

    // Simplify fraction to the simplest form, runs in constructor
    public void simplify() {
      this.sign =
          !(this.numerator <= 0 && this.denominator <= 0)
              && !(this.numerator >= 0 && this.denominator >= 0);

      this.numerator = Math.abs(this.numerator);
      this.denominator = Math.abs(this.denominator);

      int gcm = this.getGcm(this.numerator, this.denominator);
      this.numerator = this.numerator / gcm;
      this.denominator = this.denominator / gcm;
      // When fraction is zero, make sure denominator is one and no negative sign
      if (this.numerator == 0 && this.denominator != 0) {
        this.denominator = 1;
        this.sign = false;
      }
    }

    public Fraction plus(Fraction f1) {
      int num;
      if (this.sign) { // this fraction is negative
        if (f1.getSign()) { // f1 is negative
          num = (-1) * this.numerator * f1.denominator + this.denominator * (-1) * f1.numerator;
        } else { // f1 is positive
          num = (-1) * this.numerator * f1.denominator + this.denominator * f1.numerator;
        }
      } else { // this fraction is positive
        if (f1.getSign()) { // f1 is negative
          num = this.numerator * f1.denominator + this.denominator * (-1) * f1.numerator;
        } else { // f1 is positive
          num = this.numerator * f1.denominator + this.denominator * f1.numerator;
        }
      }
      int denom = this.denominator * f1.getDenominator();
      return new Fraction(num, denom);
    }

    public Fraction minus(Fraction f1) {
      int num;
      if (this.sign) { // this fraction is negative
        if (f1.getSign()) { // f1 is negative
          num = (-1) * this.numerator * f1.denominator + this.denominator * f1.numerator;
        } else { // f1 is positive
          num = (-1) * this.numerator * f1.denominator - this.denominator * f1.numerator;
        }
      } else { // this fraction is positive
        if (f1.getSign()) { // f1 is negative
          num = this.numerator * f1.denominator + this.denominator * f1.numerator;
        } else { // f1 is positive
          num = this.numerator * f1.denominator - this.denominator * f1.numerator;
        }
      }
      int denom = this.denominator * f1.getDenominator();
      return new Fraction(num, denom);
    }

    public Fraction multiply(Fraction f1) {
      int signInt = 1;
      // Either one fraction is negative will make the product fraction negative, but not for both
      // fractions are negative.
      if (this.sign && !f1.getSign() || !this.sign && f1.getSign()) {
        signInt = -1;
      }
      return new Fraction(
          signInt * this.numerator * f1.getNumerator(), this.denominator * f1.getDenominator());
    }

    public Fraction dividedBy(Fraction f1) {
      int signInt = 1;
      // Either one fraction is negative will make the product fraction negative, but not for both
      // fractions are negative.
      if (this.sign && !f1.getSign() || !this.sign && f1.getSign()) {
        signInt = -1;
      }
      return new Fraction(
          signInt * this.numerator * f1.getDenominator(), this.denominator * f1.getNumerator());
    }

    public boolean equals(Fraction f1) {
      return this.numerator == f1.getNumerator()
          && this.denominator == f1.getDenominator()
          && this.sign == f1.getSign();
    }

    public int getNumerator() {
      return this.numerator;
    }

    public int getDenominator() {
      return this.denominator;
    }

    public boolean getSign() {
      return this.sign;
    }

    public String toString() {
      String signStr = "";
      String fractionStr;
      if (this.sign) {
        signStr = "-";
      }
      if (numerator == denominator) {
        fractionStr = "1";
      } else if (denominator == 1) {
        fractionStr = Integer.toString(numerator);
      } else {
        fractionStr = numerator + "/" + denominator;
      }
      return signStr + fractionStr;
    }
  }
}
