package Salami;

import java.util.List;

public class NeuralNetwork {
    Matrix weights_ih , weights_h1, weights_h2, weights_ho , bias_h, bias_h1, bias_h2 , bias_o;
    double l_rate=0.01;

    public NeuralNetwork(int i,int h0, int h1, int h2, int o) {
        weights_ih = new Matrix(h0,i);
        weights_h1 = new Matrix(h1, h0);
        weights_h2 = new Matrix(h2, h1);
        weights_ho = new Matrix(o,h2);

        bias_h= new Matrix(h0,1);
        bias_h1=new Matrix(h1,1);
        bias_h2=new Matrix(h2,1);
        bias_o= new Matrix(o,1);

    }
    public List<Double> predict(double[] X)
    {
        Matrix input = Matrix.fromArray(X);
        Matrix hidden = Matrix.multiply(weights_ih, input);
        hidden.add(bias_h);
        hidden.sigmoid();
        Matrix hidden1 = Matrix.multiply(weights_h1, hidden);
        hidden1.add(bias_h1);
        hidden1.sigmoid();
        Matrix hidden2 = Matrix.multiply(weights_h2, hidden1);
        hidden2.add(bias_h2);
        hidden2.sigmoid();

        Matrix output = Matrix.multiply(weights_ho,hidden2);
        output.add(bias_o);
        output.sigmoid();

        return output.toArray();
    }
    public void train(double [] X,double [] Y)
    {
        Matrix input = Matrix.fromArray(X);
        Matrix hidden = Matrix.multiply(weights_ih, input);
        hidden.add(bias_h);
        hidden.sigmoid();
        Matrix hidden1 = Matrix.multiply(weights_h1, hidden);
        hidden1.add(bias_h1);
        hidden1.sigmoid();
        Matrix hidden2 = Matrix.multiply(weights_h2, hidden1);
        hidden2.add(bias_h2);
        hidden2.sigmoid();
        Matrix output = Matrix.multiply(weights_ho,hidden2);
        output.add(bias_o);
        output.sigmoid();

        Matrix target = Matrix.fromArray(Y);

        Matrix error = Matrix.subtract(target, output);
        Matrix gradient = output.dsigmoid();
        gradient.multiply(error);
        gradient.multiply(l_rate);

        Matrix hidden_T = Matrix.transpose(hidden2);
        Matrix who_delta =  Matrix.multiply(gradient, hidden_T);

        weights_ho.add(who_delta);
        bias_o.add(gradient);

        Matrix who_T = Matrix.transpose(weights_ho);
        Matrix hidden_errors = Matrix.multiply(who_T, error);

        Matrix h_gradient = hidden2.dsigmoid();
        h_gradient.multiply(hidden_errors);
        h_gradient.multiply(l_rate);

        Matrix i_T = Matrix.transpose(input);
        Matrix wih_delta = Matrix.multiply(h_gradient, i_T);

        weights_ih.add(wih_delta);
        bias_h.add(h_gradient);

    }
    public void fit(double[][]X,double[][]Y,int epochs)
    {
        for(int i=0;i<epochs;i++)
        {
            int sampleN =  (int)(Math.random() * X.length );
            this.train(X[sampleN], Y[sampleN]);
        }
    }
}
