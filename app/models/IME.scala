package models

import javax.inject.Inject

import play.api.{Environment, Logger, Play, Configuration}
import fr.magistry.utils.taigi.{EleveConfig, IMEConfig, ImeJVM, LMConfig}

/**
  * Created by pierre on 10/10/16.
  */
class IME @Inject() (configuration: Configuration) {

  val basePath = configuration.getString("IME.basePath").getOrElse(".")
  val eleveConfig = EleveConfig(5, s=>(s.length +1) /2, 0.05)
  val config = IMEConfig(basePath,eleveConfig, LMConfig(3,None,false, true))
  Logger.debug(eleveConfig.toString)
  Logger.debug(config.toString)

  val ime = new ImeJVM(config,s"$basePath/kenlm.arpa", s"$basePath/db.sqlite")

  val x = ime.computeCandidateList("oh", "", true, true)
  //ime.populate("/workdir/TaigIME2/conversions.csv", "/workdir/TaigIME2/elevedata.txt")

  Logger.debug(x.toString)


}
