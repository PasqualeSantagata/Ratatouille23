package com.example.springclient.contract;

import com.example.springclient.entity.Allergene;

public interface AllergeneContract {

    interface Model{

        void saveAllergene(Allergene allergene, AllergeneCallback allergeneCallback);

        interface  AllergeneCallback{
            void onFinished();

            void onFailure(Throwable t);

        }

    }

    interface View {

        /*TODO*/
    }

    interface Presenter{

         void saveAllergene(Allergene allergene);

    }
}
