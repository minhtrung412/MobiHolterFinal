package com.example.mobiholterfinal.model;

public class EcgDataUpload {
   String dataChannel_1;
   String dataChannel_2;
   String dataChannel_3;
   int ecgId;
   String timestamp;
   String patientId;
   int samplingRate;

    public EcgDataUpload(String dataChannel_1, String dataChannel_2, String dataChannel_3) {
        this.dataChannel_1 = dataChannel_1;
        this.dataChannel_2 = dataChannel_2;
        this.dataChannel_3 = dataChannel_3;
    }

    public EcgDataUpload(String dataChannel_1, String dataChannel_2, String dataChannel_3, int ecgId, String time_stamp, String patient_id, int sampling_rate) {
        this.dataChannel_1 = dataChannel_1;
        this.dataChannel_2 = dataChannel_2;
        this.dataChannel_3 = dataChannel_3;
        this.ecgId = ecgId;
        this.timestamp = time_stamp;
        this.patientId = patient_id;
        this.samplingRate = sampling_rate;
    }



    public int getEcgId() {
        return ecgId;
    }

    public void setEcgId(int ecgId) {
        this.ecgId = ecgId;
    }


    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public int getSamplingRate() {
        return samplingRate;
    }

    public void setSamplingRate(int samplingRate) {
        this.samplingRate = samplingRate;
    }

    public void setDataChannel_1(String dataChannel_1) {
        this.dataChannel_1 = dataChannel_1;
    }

    public void setDataChannel_2(String dataChannel_2) {
        this.dataChannel_2 = dataChannel_2;
    }

    public void setDataChannel_3(String dataChannel_3) {
        this.dataChannel_3 = dataChannel_3;
    }

    public String getDataChannel_1() {
        return dataChannel_1;
    }

    public String getDataChannel_2() {
        return dataChannel_2;
    }

    public String getDataChannel_3() {
        return dataChannel_3;
    }
}
