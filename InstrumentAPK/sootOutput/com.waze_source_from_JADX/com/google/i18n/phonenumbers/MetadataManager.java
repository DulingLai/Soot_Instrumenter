package com.google.i18n.phonenumbers;

import com.google.i18n.phonenumbers.Phonemetadata.PhoneMetadata;
import com.google.i18n.phonenumbers.Phonemetadata.PhoneMetadataCollection;
import dalvik.annotation.Signature;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

final class MetadataManager {
    private static final String ALTERNATE_FORMATS_FILE_PREFIX = "/com/google/i18n/phonenumbers/data/PhoneNumberAlternateFormatsProto";
    static final MetadataLoader DEFAULT_METADATA_LOADER = new C10591();
    static final String MULTI_FILE_PHONE_NUMBER_METADATA_FILE_PREFIX = "/com/google/i18n/phonenumbers/data/PhoneNumberMetadataProto";
    private static final String SHORT_NUMBER_METADATA_FILE_PREFIX = "/com/google/i18n/phonenumbers/data/ShortNumberMetadataProto";
    static final String SINGLE_FILE_PHONE_NUMBER_METADATA_FILE_NAME = "/com/google/i18n/phonenumbers/data/SingleFilePhoneNumberMetadataProto";
    private static final Set<Integer> alternateFormatsCountryCodes = AlternateFormatsCountryCodeSet.getCountryCodeSet();
    private static final ConcurrentHashMap<Integer, PhoneMetadata> alternateFormatsMap = new ConcurrentHashMap();
    private static final Logger logger = Logger.getLogger(MetadataManager.class.getName());
    private static final ConcurrentHashMap<String, PhoneMetadata> shortNumberMetadataMap = new ConcurrentHashMap();
    private static final Set<String> shortNumberMetadataRegionCodes = ShortNumbersRegionCodeSet.getRegionCodeSet();

    static class C10591 implements MetadataLoader {
        C10591() throws  {
        }

        public InputStream loadMetadata(String $r1) throws  {
            return MetadataManager.class.getResourceAsStream($r1);
        }
    }

    static class SingleFileMetadataMaps {
        private final Map<Integer, PhoneMetadata> countryCallingCodeToMetadata;
        private final Map<String, PhoneMetadata> regionCodeToMetadata;

        static SingleFileMetadataMaps load(String $r0, MetadataLoader $r1) throws  {
            List<PhoneMetadata> $r4 = MetadataManager.getMetadataFromSingleFileName($r0, $r1);
            HashMap $r3 = new HashMap();
            HashMap $r2 = new HashMap();
            for (PhoneMetadata $r7 : $r4) {
                $r0 = $r7.getId();
                if (PhoneNumberUtil.REGION_CODE_FOR_NON_GEO_ENTITY.equals($r0)) {
                    $r2.put(Integer.valueOf($r7.getCountryCode()), $r7);
                } else {
                    $r3.put($r0, $r7);
                }
            }
            return new SingleFileMetadataMaps($r3, $r2);
        }

        private SingleFileMetadataMaps(@Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/google/i18n/phonenumbers/Phonemetadata$PhoneMetadata;", ">;", "Ljava/util/Map", "<", "Ljava/lang/Integer;", "Lcom/google/i18n/phonenumbers/Phonemetadata$PhoneMetadata;", ">;)V"}) Map<String, PhoneMetadata> $r1, @Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/google/i18n/phonenumbers/Phonemetadata$PhoneMetadata;", ">;", "Ljava/util/Map", "<", "Ljava/lang/Integer;", "Lcom/google/i18n/phonenumbers/Phonemetadata$PhoneMetadata;", ">;)V"}) Map<Integer, PhoneMetadata> $r2) throws  {
            this.regionCodeToMetadata = Collections.unmodifiableMap($r1);
            this.countryCallingCodeToMetadata = Collections.unmodifiableMap($r2);
        }

        PhoneMetadata get(String $r1) throws  {
            return (PhoneMetadata) this.regionCodeToMetadata.get($r1);
        }

        PhoneMetadata get(int $i0) throws  {
            return (PhoneMetadata) this.countryCallingCodeToMetadata.get(Integer.valueOf($i0));
        }
    }

    private MetadataManager() throws  {
    }

    static PhoneMetadata getAlternateFormatsForCountry(int $i0) throws  {
        if (!alternateFormatsCountryCodes.contains(Integer.valueOf($i0))) {
            return null;
        }
        return getMetadataFromMultiFilePrefix(Integer.valueOf($i0), alternateFormatsMap, ALTERNATE_FORMATS_FILE_PREFIX, DEFAULT_METADATA_LOADER);
    }

    static PhoneMetadata getShortNumberMetadataForRegion(String $r0) throws  {
        if (!shortNumberMetadataRegionCodes.contains($r0)) {
            return null;
        }
        return getMetadataFromMultiFilePrefix($r0, shortNumberMetadataMap, SHORT_NUMBER_METADATA_FILE_PREFIX, DEFAULT_METADATA_LOADER);
    }

    static Set<String> getSupportedShortNumberRegions() throws  {
        return Collections.unmodifiableSet(shortNumberMetadataRegionCodes);
    }

    static <T> PhoneMetadata getMetadataFromMultiFilePrefix(@Signature({"<T:", "Ljava/lang/Object;", ">(TT;", "Ljava/util/concurrent/ConcurrentHashMap", "<TT;", "Lcom/google/i18n/phonenumbers/Phonemetadata$PhoneMetadata;", ">;", "Ljava/lang/String;", "Lcom/google/i18n/phonenumbers/MetadataLoader;", ")", "Lcom/google/i18n/phonenumbers/Phonemetadata$PhoneMetadata;"}) T $r0, @Signature({"<T:", "Ljava/lang/Object;", ">(TT;", "Ljava/util/concurrent/ConcurrentHashMap", "<TT;", "Lcom/google/i18n/phonenumbers/Phonemetadata$PhoneMetadata;", ">;", "Ljava/lang/String;", "Lcom/google/i18n/phonenumbers/MetadataLoader;", ")", "Lcom/google/i18n/phonenumbers/Phonemetadata$PhoneMetadata;"}) ConcurrentHashMap<T, PhoneMetadata> $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(TT;", "Ljava/util/concurrent/ConcurrentHashMap", "<TT;", "Lcom/google/i18n/phonenumbers/Phonemetadata$PhoneMetadata;", ">;", "Ljava/lang/String;", "Lcom/google/i18n/phonenumbers/MetadataLoader;", ")", "Lcom/google/i18n/phonenumbers/Phonemetadata$PhoneMetadata;"}) String $r2, @Signature({"<T:", "Ljava/lang/Object;", ">(TT;", "Ljava/util/concurrent/ConcurrentHashMap", "<TT;", "Lcom/google/i18n/phonenumbers/Phonemetadata$PhoneMetadata;", ">;", "Ljava/lang/String;", "Lcom/google/i18n/phonenumbers/MetadataLoader;", ")", "Lcom/google/i18n/phonenumbers/Phonemetadata$PhoneMetadata;"}) MetadataLoader $r3) throws  {
        PhoneMetadata $r5 = (PhoneMetadata) $r1.get($r0);
        if ($r5 != null) {
            return $r5;
        }
        $r2 = $r2 + "_" + $r0;
        List $r7 = getMetadataFromSingleFileName($r2, $r3);
        if ($r7.size() > 1) {
            logger.log(Level.WARNING, "more than one metadata in file " + $r2);
        }
        PhoneMetadata $r10 = (PhoneMetadata) $r7.get(0);
        $r5 = (PhoneMetadata) $r1.putIfAbsent($r0, $r10);
        return $r5 == null ? $r10 : $r5;
    }

    static SingleFileMetadataMaps getSingleFileMetadataMaps(@Signature({"(", "Ljava/util/concurrent/atomic/AtomicReference", "<", "Lcom/google/i18n/phonenumbers/MetadataManager$SingleFileMetadataMaps;", ">;", "Ljava/lang/String;", "Lcom/google/i18n/phonenumbers/MetadataLoader;", ")", "Lcom/google/i18n/phonenumbers/MetadataManager$SingleFileMetadataMaps;"}) AtomicReference<SingleFileMetadataMaps> $r0, @Signature({"(", "Ljava/util/concurrent/atomic/AtomicReference", "<", "Lcom/google/i18n/phonenumbers/MetadataManager$SingleFileMetadataMaps;", ">;", "Ljava/lang/String;", "Lcom/google/i18n/phonenumbers/MetadataLoader;", ")", "Lcom/google/i18n/phonenumbers/MetadataManager$SingleFileMetadataMaps;"}) String $r1, @Signature({"(", "Ljava/util/concurrent/atomic/AtomicReference", "<", "Lcom/google/i18n/phonenumbers/MetadataManager$SingleFileMetadataMaps;", ">;", "Ljava/lang/String;", "Lcom/google/i18n/phonenumbers/MetadataLoader;", ")", "Lcom/google/i18n/phonenumbers/MetadataManager$SingleFileMetadataMaps;"}) MetadataLoader $r2) throws  {
        SingleFileMetadataMaps $r4 = (SingleFileMetadataMaps) $r0.get();
        if ($r4 != null) {
            return $r4;
        }
        $r0.compareAndSet(null, SingleFileMetadataMaps.load($r1, $r2));
        return (SingleFileMetadataMaps) $r0.get();
    }

    private static List<PhoneMetadata> getMetadataFromSingleFileName(@Signature({"(", "Ljava/lang/String;", "Lcom/google/i18n/phonenumbers/MetadataLoader;", ")", "Ljava/util/List", "<", "Lcom/google/i18n/phonenumbers/Phonemetadata$PhoneMetadata;", ">;"}) String $r0, @Signature({"(", "Ljava/lang/String;", "Lcom/google/i18n/phonenumbers/MetadataLoader;", ")", "Ljava/util/List", "<", "Lcom/google/i18n/phonenumbers/Phonemetadata$PhoneMetadata;", ">;"}) MetadataLoader $r1) throws  {
        InputStream $r2 = $r1.loadMetadata($r0);
        if ($r2 == null) {
            throw new IllegalStateException("missing metadata: " + $r0);
        }
        List $r6 = loadMetadataAndCloseInput($r2).getMetadataList();
        if ($r6.size() != 0) {
            return $r6;
        }
        throw new IllegalStateException("empty metadata: " + $r0);
    }

    private static PhoneMetadataCollection loadMetadataAndCloseInput(InputStream $r0) throws  {
        Throwable $r6;
        ObjectInputStream $r3 = null;
        try {
            ObjectInputStream $r2 = new ObjectInputStream($r0);
            try {
                PhoneMetadataCollection $r1 = new PhoneMetadataCollection();
                $r1.readExternal($r2);
                if ($r2 != null) {
                    try {
                        $r2.close();
                        return $r1;
                    } catch (IOException $r8) {
                        logger.log(Level.WARNING, "error closing input stream (ignored)", $r8);
                        return $r1;
                    }
                }
                $r0.close();
                return $r1;
            } catch (IOException $r7) {
                throw new RuntimeException("cannot load/parse metadata", $r7);
            } catch (Throwable th) {
                $r6 = th;
                $r3 = $r2;
                if ($r3 == null) {
                    $r0.close();
                } else {
                    try {
                        $r3.close();
                    } catch (IOException $r11) {
                        logger.log(Level.WARNING, "error closing input stream (ignored)", $r11);
                    }
                }
                throw $r6;
            }
        } catch (IOException $r4) {
            throw new RuntimeException("cannot load/parse metadata", $r4);
        } catch (Throwable th2) {
            $r6 = th2;
            if ($r3 == null) {
                $r3.close();
            } else {
                $r0.close();
            }
            throw $r6;
        }
    }
}
