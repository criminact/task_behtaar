package androidx.databinding;

public class DataBinderMapperImpl extends MergedDataBinderMapper {
  DataBinderMapperImpl() {
    addMapper(new com.noobcode.task_behtaar.DataBinderMapperImpl());
  }
}
