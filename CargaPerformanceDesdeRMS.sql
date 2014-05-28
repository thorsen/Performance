INSERT INTO TPROPERF_L 
          SELECT 1, PERF_FECHAJOR, PERF_SECCION, PERF_CELULA, PERF_NROMAQ, PERF_LABORABLE, 
          SUM(PERF_PRODUCIDO) AS PERF_PRODUCIDO, 
          SUM(PERF_TPCAMBIO) AS PERF_TPCAMBIO, 
          SUM(PERF_TPREGLAJE) AS PERF_TPREGLAJE, 
          SUM(PERF_TPTINTAS) AS PERF_TPTINTAS, 
          SUM(PERF_TPMATERIAL) AS PERF_TPMATERIAL, 
          SUM(PERF_TPMANTENI) AS PERF_TPMANTENI, 
          SUM(PERF_TPLIMPIEZA) AS PERF_TPLIMPIEZA, 
          SUM(PERF_TPLIMPIEZAC) AS PERF_TPLIMPIEZAC, 
          SUM(PERF_TVACIO) AS PERF_TVACIO, 
          SUM(PERF_TPNOATEND) AS PERF_TPNOATEND, 
          SUM(PERF_TPSINMOT) AS PERF_TPSINMOT, 
          SUM(PERF_TPUTIL) AS PERF_TPUTIL, 
          SUM(PERF_TBONO) AS PERF_TBONO, 
          SUM(PERF_TBOCADILLO) AS PERF_TBOCADILLO,  
          SUM(PERF_TPARO) AS PERF_TPARO, 
          SUM(PERF_TNPRODP) AS PERF_TNPRODP, 
          SUM(PERF_TNPRODNP) AS PERF_TNPRODNP, 
          SUM(PERF_TMOD) AS PERF_TMOD, 
          SUM(PERF_TPMANINTER) AS PERF_TPMANINTER, 
          SUM(PERF_TPIMASD) AS PERF_TPIMASD, 
          SUM(PERF_TPREFORMA) AS PERF_TPREFORMA, 
          SUM(PERF_TMUESTRAS) AS PERF_TMUESTRAS, 
          SUM(PERF_TNOCONTROL) AS PERF_TNOCONTROL, 
          SUM(PERF_TDESCANSO) AS PERF_TDESCANSO, 
          SUM(PERF_TNECESPERS) AS PERF_TNECESPERS, 
          SUM(PERF_TNOIMPUT) AS PERF_TNOIMPUT, 
          SUM(PERF_SWPRODTEOR) AS PERF_SWPRODTEOR, 
          SUM(PERF_TPDECICOME) AS PERF_TPDECICOME, 
          SUM(PERF_PRODEQUIV) AS PERF_PRODEQUIV, 
          SUM(PERF_TPCARTACOL) AS PERF_TPCARTACOL, 
          SUM(PERF_PRODTEOR) AS PERF_PRODTEOR, 
          SUM(PERF_TPRESENCIA) AS PERF_TPRESENCIA, 
          SUM(PERF_TPAROCONOF) AS PERF_TPAROCONOF, 
          SUM(PERF_TPESPEURGE) AS PERF_TPESPEURGE, 
          SUM(PERF_TPAROPROGR) AS PERF_TPAROPROGR, 
          SUM(PERF_CONSUMIDO) AS PERF_CONSUMIDO,
          SUM(PERF_CADENA) AS PERF_CADENA,
          SUM(PERF_TPOCAMB) AS PERF_TPOCAMB,
          SUM(PERF_TPOREGL) AS PERF_TPOREGL,
          SUM(PERF_TPOTINT) AS PERF_TPOTINT,
          SUM(PERF_PRODCAMB) AS PERF_PRODCAMB,
          SUM(PERF_CAPSUCAMB) AS PERF_CAPSUCAMB,
          SUM(PERF_NROCAMBC) AS PERF_NROCAMBC,
          SUM(PERF_TPOCAMBC) AS PERF_TPOCAMBC,
          SUM(PERF_TPOREGLC) AS PERF_TPOREGLC,
          SUM(PERF_TPOTINTC) AS PERF_TPOTINTC,
          MIN(MMAQ_CONTCAPSULAS) AS MMAQ_CONTCAPSULAS,
          SUM(CASE WHEN PERF_PRODUCIDO <= (PERF_CONSUMIDO - PERF_CADENA) AND MMAQ_CONTCAPSULAS = 'S' AND (PERF_SECCION = 59 OR PERF_SECCION = 60 OR PERF_SECCION = 91) THEN PERF_PRODUCIDO ELSE NULL END) AS PERF_PROD_DESP,
          SUM(CASE WHEN PERF_PRODUCIDO <= (PERF_CONSUMIDO - PERF_CADENA) AND MMAQ_CONTCAPSULAS = 'S' AND (PERF_SECCION = 59 OR PERF_SECCION = 60 OR PERF_SECCION = 91) THEN PERF_CONSUMIDO ELSE NULL END) AS PERF_CONS_DESP,
          SUM(CASE WHEN PERF_PRODUCIDO <= (PERF_CONSUMIDO - PERF_CADENA) AND MMAQ_CONTCAPSULAS = 'S' AND (PERF_SECCION = 59 OR PERF_SECCION = 60 OR PERF_SECCION = 91) THEN PERF_CADENA ELSE NULL END) AS PERF_CAD_DESP,
          SUM(CASE WHEN MMAQ_CONTCAPSULAS = 'S' AND (PERF_SECCION = 59 OR PERF_SECCION = 60 OR PERF_SECCION = 91) THEN PERF_CAPSUCAMB ELSE NULL END) AS PERF_CCAMB_DESP,
          SUM(CASE WHEN MMAQ_CONTCAPSULAS = 'S' AND (PERF_SECCION = 59 OR PERF_SECCION = 60 OR PERF_SECCION = 91) THEN PERF_PRODCAMB ELSE NULL END) AS PERF_PCAMB_DESP
          FROM FPROPERF_L, FFABMMAQ
          --WHERE PERF_FECHAJOR > 20050000 AND PERF_FECHAJOR < 20070000
          --WHERE PERF_FECHAJOR > 20070000 AND PERF_FECHAJOR < 20090000
          --WHERE PERF_FECHAJOR > 20090000 AND PERF_FECHAJOR < 20110000
          --WHERE PERF_FECHAJOR > 20110000 AND PERF_FECHAJOR < 20130000
          WHERE PERF_FECHAJOR > 20130000 AND PERF_FECHAJOR < 20150000
          AND PERF_NROMAQ = MMAQ_NROMAQ 
          GROUP BY PERF_FECHAJOR, PERF_SECCION, PERF_CELULA, PERF_NROMAQ, PERF_LABORABLE